package com.ibrascan.borescope;

import android.media.MediaPlayer;
import android.util.Log;

public class Media {
    private final String TAG = "Media";
    private MediaPlayer mediaPlayer = null;

    public void playShutter() {
        try{
            Stream.TakePhoto = true;
            Log.e(TAG, "Media Play Shutter!");
        } catch (Exception e) {
            Log.e(TAG, "Music Error");
            e.printStackTrace();
        }
    }
}
