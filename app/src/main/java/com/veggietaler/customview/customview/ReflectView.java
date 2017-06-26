package com.veggietaler.customview.customview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/19.
 */

public class ReflectView extends View {

    private Bitmap mSrcBitmap, mRefBitmap;//原味图和倒影位图
    private Paint mPaint;//画笔
    private PorterDuffXfermode porterDuffXfermode;//混合模式
    private int x, y;//位图起始坐标
    private int screenWidth, screenHeight;//屏幕宽高

    public ReflectView(Context context) {
        this(context, null);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initRes(context);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x + mSrcBitmap.getWidth(), y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    private void initRes(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;            //屏幕宽度
        screenHeight = metrics.heightPixels;        //屏幕高

        //获取源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        //实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);//y轴相反

        //生成倒影图
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

//        x = screenWidth / 2 - mSrcBitmap.getWidth() / 2;
//        y = screenHeight / 2 - mSrcBitmap.getHeight() / 2;

        x = 100;
        y = 100;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mSrcBitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(), x + mSrcBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);//先绘制的是目标图
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawRect(x, y+mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);//混合倒影图片和新画的矩形部分（矩形半透明向透明渐变）
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
