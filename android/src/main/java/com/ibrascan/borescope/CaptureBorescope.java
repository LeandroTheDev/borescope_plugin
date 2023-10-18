package com.ibrascan.borescope;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.json.JSONObject;

import io.flutter.plugin.common.MethodChannel;

public class CaptureBorescope {
    private Activity activity;
    private Context context;
    private MethodChannel.Result result;

    final int MY_REQ_CODE = 1;
    private static final String[] networkPermissions = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    CaptureBorescope(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void initStream(MethodChannel.Result result, Stream stream) {
        this.result = result;
        stream.destroy();
        result.success("success open borescope");
    }

    public String verifyBorescopeSSID() {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            String ssid = wifiInfo.getSSID();
            if(ssid.toLowerCase().contains("HOWiFi".toLowerCase())) {
                return ssid;
            } else {
                return "not borescope " + ssid.toString();
            }
        } else {
            return "no permission";
        }
     }
}
