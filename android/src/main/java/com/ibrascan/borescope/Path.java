package com.ibrascan.borescope;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Path {
    static private final String TAG = "Path";
    public static SdcardSelector sdcardSelector = SdcardSelector.BUILT_IN;
    public enum SdcardSelector {
        BUILT_IN
    }

    public static String getRootPath() {
        if(sdcardSelector == SdcardSelector.BUILT_IN) {
            return SDCardUtils.getFirstExternPath();
        }
        String secondExternPath = SDCardUtils.getSecondExternPath();
        if (secondExternPath == null) {
            secondExternPath = null;
        }
        return secondExternPath;
    }

    public static String savePhoto(String str, String str2, Context context, Bitmap bitmap, Handler handler) {
        if (getRootPath() != null) {
            try {
                File file = new File(str);
                if(!file.exists()) {
                    file.mkdirs();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("create folder ");
                    stringBuilder.append(str);
                    Log.d(TAG, stringBuilder.toString());
                }
                file = new File(str, str2);
                if(!file.exists()) {
                    file.createNewFile();
                }
                str = file.getAbsolutePath();
                Log.e(TAG, str);
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
                fileOutputStream.close();
                Message message = new Message();
                message.what = HandlerParams.TAKE_PICTURE;
                handler.sendMessage(message);
                return str;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static int getSdcardAvailableSize() {
        StatFs statFs = new StatFs(new File(getRootPath()).getPath());
        return (int) (((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1024) / 1024);
    }
}
