package com.ibrascan.borescope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.metrics.Event;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wifiview.nativelibs.NativeLibs;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.flutter.plugin.common.EventChannel;

public class Stream {
    protected static final ThreadPoolExecutor EXECUTER = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue());
    protected static final String TAG = "Stream";

    //Manipulation
    public static boolean TakePhoto = false;
    private boolean isNeedTakePhoto = false;
    public boolean isRunning = false;
    //Format
    public static int VideoFormat = 0;
    public static int VideoHeight = 480;
    public static int VideoTmpHeight = 0;
    public static int VideoTmpWidth = 0;
    public static int VideoWidth = 640;
    public VideoParams videoParams = new VideoParams();
    public String path;
    public byte[] imagem;
    private Media media;
    private int mReturn = 0;
    public static boolean takePhoto = false;
    //Hardware
    public Context context;
    public String file;
    public Handler handler;
    public NativeLibs nativelibs;

    public class VideoParams {
        public int stream_pass_ok;
        public int video_format;
        public int video_height;
        public int video_width;
    }

    Stream(Context context, Handler handler, String str) {
        this.context = context;
        this.handler = handler;
        this.path = str;
        this.file = "boroscopio.jpg";
    }

    public void startStream() {
        this.nativelibs = new NativeLibs();
        if(!this.isRunning) {
            EXECUTER.execute(new Runnable() {
                @Override
                public void run() {
                    Stream.this.isRunning = true;
                    Stream.this.nativelibs.startPreview();
                    //Empty Video Checker
                    int i = 0;
                    do {
                        Stream.this.videoParams.video_format = Stream.this.nativelibs.getVideoFormat(Stream.this.videoParams);
                        Stream.this.msleep(100);
                        if(!Stream.this.isRunning) {
                            break;
                        } else if (i > 10) {
                            Stream.this.handler.sendEmptyMessage(HandlerParams.ERROR_NOT_FOUND);
                        } else {
                            i++;
                        }
                    } while (Stream.this.videoParams.video_format <= 0);
                    //Console Log
                    Stream.VideoFormat = Stream.this.videoParams.video_format;
                    Stream.VideoWidth = Stream.this.videoParams.video_width;
                    Stream.VideoHeight = Stream.this.videoParams.video_height;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("videoWidth");
                    stringBuilder.append(Stream.VideoWidth);
                    stringBuilder.append(Stream.VideoHeight);
                    Log.i(TAG, stringBuilder.toString());
                    Stream.this.doExecuteMJPEG();
                    if(Stream.this.nativelibs != null) {
                        Stream.this.nativelibs.stopPreview();
                    }
                }
            });
        }
    }

    //Utils
    public void msleep(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopStream() {
        this.isRunning = false;
        NativeLibs.nativeAVIRecStop();
    }

    public void destroy() {
        stopStream();
        this.nativelibs.destoryCamera();
        this.nativelibs = null;
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) i) / ((float) width);
        float f2 = ((float) i2) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public void doExecuteMJPEG() {
        System.currentTimeMillis();
        Bitmap bitmap = null;
        while (this.isRunning) {
            byte[] oneFrameBuffer = this.nativelibs.getOneFrameBuffer();
            if (oneFrameBuffer == null) {
                msleep(5);
                this.mReturn++;
            } else {
                int i;
                bitmap = BitmapFactory.decodeByteArray(oneFrameBuffer, 0, oneFrameBuffer.length);
                if (bitmap != null) {
                    VideoWidth = bitmap.getWidth();
                    i = bitmap.getHeight();
                    VideoHeight = i;
                    if (VideoTmpWidth == 0) {
                        VideoTmpWidth  = VideoWidth;
                        VideoTmpHeight = i;
                    }
                    if (!(VideoTmpWidth == VideoWidth && VideoTmpHeight == i)) {
                        VideoTmpWidth = 0;
                    }
                    Bitmap imageBitmap = scaleBitmap(bitmap, 1600, 1200);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    byte[] b = baos.toByteArray();
                    this.imagem = b;
                    Message message = new Message();
                    message.what = HandlerParams.STREAM_VIDEO;
                    this.handler.sendMessage(message);
                }
            }
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    public void takePhoto() {
        this.media.playShutter();
        if (Path.getSdcardAvailableSize() > 100) {
            this.isNeedTakePhoto = true;
        } else {
            this.handler.sendEmptyMessage(18);
        }
    }
}
