package com.ibrascan.borescope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.core.view.ViewCompat;

public class View extends android.view.SurfaceView implements KeyEvent.Callback {
    private Canvas canvas = null;
    private SurfaceHolder holder = null;
    private Paint paint = null;
    private Rect rect = null;

    public View(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SurfaceHolder holder = getHolder();
        this.holder = holder;
        holder.addCallback((SurfaceHolder.Callback) this);
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-16776961);
        this.paint.setAntiAlias(true);
        this.holder.setFormat(-2);
        this.rect = new Rect(0,0 , getWidth(), getHeight());
    }

    public void SetBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            Canvas lockCanvas = this.holder.lockCanvas(this.rect);
            this.canvas = lockCanvas;
            if(lockCanvas != null) {
                lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                Paint paint = new Paint();
                this.paint = paint;
                this.canvas.drawBitmap(bitmap, null, this.rect, paint);
                SurfaceHolder surfaceHolder = this.holder;
                if (surfaceHolder != null) {
                    surfaceHolder.unlockCanvasAndPost(this.canvas);
                }
            }
        }
    }
}
