package com.ibrascan.borescope;

import android.content.Context;
import android.os.Handler;

import com.wifiview.nativelibs.NativeLibs;

public class Socket {
    private Context context;
    public Handler handler;
    public boolean threadRunning = false;

    public Socket(Context context) {
        this.context = context;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void startRunKeyThread() {
        this.threadRunning = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (Socket.this.threadRunning) {
                    int nativeCmdGetRemoteKey = NativeLibs.nativeCmdGetRemoteKey();
                    if(nativeCmdGetRemoteKey == 1) {
                        Socket.this.handler.sendEmptyMessage(21);
                    } else if (nativeCmdGetRemoteKey == 2) {
                        Socket.this.handler.sendEmptyMessage(22);
                    } else if (nativeCmdGetRemoteKey == 3) {
                        Socket.this.handler.sendEmptyMessage(23);
                    } else if (nativeCmdGetRemoteKey == 4) {
                        Socket.this.handler.sendEmptyMessage(24);
                    }
                    Socket.this.msleep(200);
                }
            }
        }).start();
    }

    public void msleep(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
