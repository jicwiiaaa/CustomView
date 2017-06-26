package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/20.
 */

public class DreamEffectView extends View {

    private Paint mBitmapPaint;
    private Paint mShaderPaint;//shader画笔
    private Bitmap mBitmap, darkCornerBitmap;
    private PorterDuffXfermode porterDuffXfermode;
    private int x, y;
    private int screenWidth, screenHeight;

    public DreamEffectView(Context context) {
        this(context, null);
    }

    public DreamEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        initRes(context);
        initPaint();
    }

    private void initRes(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;            //屏幕宽度
        screenHeight = metrics.heightPixels;        //屏幕高

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
        int x = screenWidth / 2 - mBitmap.getWidth() / 2;
        int y = screenHeight / 2 - mBitmap.getHeight() / 2;
    }

    private void initPaint() {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        // 去饱和、提亮、色相矫正  (暂照搬)
        mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F,
                0.0821F, 0.9145F, 0.0634F, 0, 6.79F,
                0.2019F, 0.1097F, 0.7483F, 0, 6.79F,
                0, 0, 0, 1, 0}));


        //实例化混合模式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        mShaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        mShaderPaint.setShader(new RadialGradient(screenWidth / 2, screenHeight / 2, screenHeight * 6 / 8, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP));
        // 根据我们源图的大小生成暗角Bitmap
        darkCornerBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // 将该暗角Bitmap注入Canvas
        Canvas canvas = new Canvas(darkCornerBitmap);

        // 计算径向渐变半径
        float radiu = canvas.getHeight() * (2F / 3F);

        // 实例化径向渐变
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth() / 2F, canvas.getHeight() / 2F, radiu, new int[] { 0, 0, 0xAA000000 }, new float[] { 0F, 0.7F, 1.0F }, Shader.TileMode.CLAMP);

        // 实例化一个矩阵
        Matrix matrix = new Matrix();

        // 设置矩阵的缩放
        matrix.setScale(canvas.getWidth() / (radiu * 2F), 1.0F);

        // 设置矩阵的预平移
        matrix.preTranslate(((radiu * 2F) - canvas.getWidth()) / 2F, 0);

        // 将该矩阵注入径向渐变
        radialGradient.setLocalMatrix(matrix);

        // 设置画笔Shader
        mShaderPaint.setShader(radialGradient);

        // 绘制矩形
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sc = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawColor(0xcc1c093e);

        mBitmapPaint.setXfermode(porterDuffXfermode);

        canvas.drawBitmap(mBitmap, x, y, mBitmapPaint);

        mBitmapPaint.setXfermode(null);

        canvas.restoreToCount(sc);

//        canvas.drawRect(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), mShaderPaint);
        canvas.drawBitmap(darkCornerBitmap, x, y, null);
    }
}
