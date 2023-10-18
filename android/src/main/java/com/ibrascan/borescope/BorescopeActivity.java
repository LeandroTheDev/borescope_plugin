package com.ibrascan.borescope;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

public class BorescopeActivity extends FlutterActivity {
    //Hardware
    private Context context;
    private Activity activity;
    //Borescope
    private Stream stream;
    private Socket socket;
    //Borescope Functions
    public void initialization(MethodChannel.Result result, Handler handler){
        context = getContext();
        activity = getActivity();
        stream = new Stream(getContext(), handler, getTempFolder(this));
        result.success("success initialized");
    }

    public void destroy(MethodChannel.Result result) {
        stream.destroy();
        stream.isRunning = false;
        result.success("success destroyed");
    }

    public void iniciarStream(MethodChannel.Result result){
        stream.startStream();
        result.success("success opened");
    }

    //Utils
    public <T extends View> T internalFindViewById(int i) {
        return findViewById(i);
    }
    public static String getTempFolder(Context context) {
        String format = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
        File file = new File(format);
        if (!file.exists()) {
            String str = "Ibrascan";
            file.mkdirs();
            if (file.exists()) {
            }
        }
        return format;
    }
    public void tirarFoto() {
        this.stream.takePhoto();
    }
    public String atualizarImagem() {
        byte[] imagem = stream.imagem;
        String encodedImage = Base64.encodeToString(imagem, Base64.DEFAULT);
        return encodedImage;
    }
    public String verificarSSID() {
        final CaptureBorescope borescope = new CaptureBorescope(context, activity);
        String ssid = borescope.verifyBorescopeSSID();
        return ssid;
    }
}
