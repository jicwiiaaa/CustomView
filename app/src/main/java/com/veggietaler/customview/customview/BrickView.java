package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/19.
 */

public class BrickView extends View {
    private Paint mPaintFill, mPaintStroke;//描边和填充的画笔对象
    private BitmapShader mBitmapShader;//bitmap着色器
    private float touchX, touchY;//触摸点坐标
    public BrickView(Context context) {
        this(context, null);
    }

    public BrickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setColor(0xFF000000);
        mPaintStroke.setStrokeWidth(5);

        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.brick);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mPaintFill.setShader(mBitmapShader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchX = event.getX();
            touchY = event.getY();

            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.DKGRAY);

        canvas.drawCircle(touchX, touchY, 300, mPaintStroke);
        canvas.drawCircle(touchX, touchY, 300, mPaintFill);
    }
}
