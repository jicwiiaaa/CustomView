package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/19.
 */

public class BitmapShaderView extends View {

    private final static int RECT_SIZE = 800;//矩形大小
    private Paint mPaint;
    private int left, top, right, bottom;//矩形左上右下点的坐标

    public BitmapShaderView(Context context) {
        this(context, null);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initRes(context);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP));
    }

    private void initRes(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;            //屏幕宽度
        int screenHeight = metrics.heightPixels;        //屏幕高度

        left = screenWidth / 2 - RECT_SIZE / 2;
        top = screenHeight / 2 - RECT_SIZE / 2;
        right = screenWidth / 2 + RECT_SIZE / 2;
        bottom = screenHeight / 2 + RECT_SIZE / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
