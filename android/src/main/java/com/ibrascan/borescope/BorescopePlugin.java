package com.ibrascan.borescope;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** BorescopePlugin */
public class BorescopePlugin implements FlutterPlugin, MethodCallHandler {
  //Channel
  private MethodChannel channel;
  private EventChannel streamChannel;
  private EventChannel.EventSink eventSink;
  //Handler
  Handler handler;
  //Activity
  BorescopeActivity borescopeActivity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    //Methods
    channel = new MethodChannel(binding.getBinaryMessenger(), "com.ibrascan.borescope/main");
    channel.setMethodCallHandler(this);
    //Image Streamer
    streamChannel = new EventChannel(binding.getBinaryMessenger(), "com.ibrascan.borescope/image");
    streamChannel.setStreamHandler(new EventChannel.StreamHandler() {
      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        eventSink = events;
      }

      @Override
      public void onCancel(Object arguments) {
        eventSink = null;
      }
    });
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("init")) {
      borescopeActivity = new BorescopeActivity();
        handler = handler = new Handler(new Handler.Callback() {
          @Override
          public boolean handleMessage(@NonNull Message message) {
            int i = message.what;
            switch(i) {
              case HandlerParams.REMOTE_TAKE_PHOTO: borescopeActivity.tirarFoto(); return true;
              case HandlerParams.ERROR_NOT_FOUND: eventSink.success("noconnection"); return true;
              case HandlerParams.TAKE_PICTURE: result.success(borescopeActivity.atualizarImagem()); return true;
              case HandlerParams.SDCARD_FULL: result.success("noimage"); return true;
              default: eventSink.success("null"); return true;
            }
          }
        });
        borescopeActivity.initialization(result, handler);
    } 
    else if  (call.method.equals("destroy")) {
      borescopeActivity.destroy(result);
    } 
    else if (call.method.equals("verifySSID")) {
      String ssid = borescopeActivity.verificarSSID();
      result.success(ssid);
    } 
    else if (call.method.equals("openBorescope")) {
      borescopeActivity.iniciarStream(result);
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
