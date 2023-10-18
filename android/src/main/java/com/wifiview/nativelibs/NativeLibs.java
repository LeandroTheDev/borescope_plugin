package com.wifiview.nativelibs;

import android.graphics.Bitmap;

import com.ibrascan.borescope.Stream;

public class NativeLibs {
    public static final short FORMAT_VIDEO_H264_I = (short) 2;
    public static final short FORMAT_VIDEO_H264_P = (short) 3;
    public static final short FORMAT_VIDEO_MJPEG = (short) 1;
    public static final short FORMAT_VIDEO_YUYV = (short) 4;
    public static final int ML_PORT_SERIES3 = 8030;
    public static final int ML_PORT_SERIES5 = 7060;
    public static final short STREAM_PASS_DEFAULT = (short) 0;
    public static final short STREAM_PASS_ERR = (short) 2;
    public static final short STREAM_PASS_OK = (short) 1;
    private long mNativePtr = nativeCreateCamera();

    static {
        System.loadLibrary((String)"mlcamera-2.5");
    }

    private static native byte[] nativeABGR2YUV(long j, int i);

    public static native void nativeAVIClose();

    public static native byte[] nativeAVIGetFrameAtIndex(int i);

    public static native byte[] nativeAVIGetFrameAtTime(double d);

    public static native int nativeAVIGetTotalFrame();

    public static native double nativeAVIGetTotalTime();

    public static native byte[] nativeAVIGetVoiceAtTime(double d);

    public static native boolean nativeAVIOpen(String str);

    public static native void nativeAVIRecAddData(byte[] bArr, int i);

    public static native void nativeAVIRecAddWav(byte[] bArr, int i);

    public static native int nativeAVIRecGetTimestamp();

    public static native void nativeAVIRecSetAudioParams(int i, int i2, int i3);

    public static native void nativeAVIRecSetParams(int i, int i2, int i3);

    public static native boolean nativeAVIRecStart(String str);

    public static native void nativeAVIRecStop();

    public static native void nativeAddMP4VideoData(byte[] bArr, int i);

    public static native int nativeCmdClearPassword();

    public static native int nativeCmdGetBattery();

    public static native int nativeCmdGetPWM();

    public static native int nativeCmdGetRemoteKey();

    public static native byte[] nativeCmdGetResolution();

    public static native int nativeCmdGetVideoFormat();

    public static native int nativeCmdSendReboot();

    public static native int nativeCmdSetChannel(int i);

    public static native int nativeCmdSetName(String str);

    public static native int nativeCmdSetPWM(int i);

    public static native int nativeCmdSetPassword(String str);

    public static native int nativeCmdSetResolution(int i, int i2, int i3);

    public static native int nativeCmdSetStreamPasswd(String str);

    public static native int nativeCmdSetSwitchMode();

    public static native int nativeCmdSetVideoFormat(int i);

    public static native int nativeCmdSwitchCam();

    private static native long nativeCreateCamera();

    private static native void nativeDestroyCamera(long j);

    private static native int nativeGetAccelerometer(long j);

    private static native byte[] nativeGetFrameBuffer(long j);

    public static native byte[] nativeGetPassErrorBuf();

    private static native int nativeGetVideoFormat(long j, Stream.VideoParams videoParams);

    private static native int nativeGetVideoFrameRate(long j);

    private static native void nativePushBmpData(long j, Bitmap bitmap);

    private static native int nativeSetStreamPasswd(long j, String str);

    public static native int nativeStartMP4Record(String str, int i, int i2);

    private static native boolean nativeStartPreview(long j, int i, int i2);

    public static native void nativeStopMP4Record();

    private static native void nativeStopPreview(long j);

    public static native int nativeUsbDecodeData(long j, byte[] bArr, int i);

    private static native int nativeYUV2ABGR(long j, Bitmap bitmap);



    public void destoryCamera() {
        nativeDestroyCamera(this.mNativePtr);
        this.mNativePtr = 0;
    }

    public int decodeUsbData(byte[] bArr, int i) {
        return nativeUsbDecodeData(this.mNativePtr, bArr, i);
    }

    public boolean startPreview() {
        return nativeStartPreview(this.mNativePtr, ML_PORT_SERIES5, ML_PORT_SERIES3);
    }

    public void stopPreview() {
        nativeStopPreview(this.mNativePtr);
    }

    public byte[] getOneFrameBuffer() {
        return nativeGetFrameBuffer(this.mNativePtr);
    }

    public int drawYUV2ARGB(Bitmap bitmap) {
        return nativeYUV2ABGR(this.mNativePtr, bitmap);
    }

    public int getVideoFormat(Stream.VideoParams videoParams) {
        return nativeGetVideoFormat(this.mNativePtr, videoParams);
    }

    public void pushBmpDataQueue(Bitmap bitmap) {
        nativePushBmpData(this.mNativePtr, bitmap);
    }

    public byte[] convertBMP2YUV(int i) {
        return nativeABGR2YUV(this.mNativePtr, i);
    }

    public int getVideoFrameRate() {
        return nativeGetVideoFrameRate(this.mNativePtr);
    }

    public int getAccelerometer() {
        return nativeGetAccelerometer(this.mNativePtr);
    }

    public int setStreamPasswd(String str) {
        return nativeSetStreamPasswd(this.mNativePtr, str);
    }
}
