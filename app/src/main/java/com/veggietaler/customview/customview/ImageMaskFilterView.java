package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/19.
 */

public class ImageMaskFilterView extends View {
    private Paint shadowPaint;//阴影位图画笔
    private Bitmap srcBitmap;//位图
    private Bitmap shadowBitmap;//阴影位图
    private int x,y;//位图绘制时左上角坐标
    private int screenHeight, screenWidth;
    public ImageMaskFilterView(Context context) {
        this(context, null);
    }

    public ImageMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        initPaint();
        initRes(context);

    }

    /**
     * 初始化资源
     * @param context
     */
    private void initRes(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;            //屏幕宽度
        screenHeight = metrics.heightPixels;        //屏幕高

        //获取位图
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pic1);
        //获取位图的alpha通道图
        shadowBitmap = srcBitmap.extractAlpha();

        x = screenWidth / 2 - srcBitmap.getWidth() / 2;
        y = screenHeight / 2 - srcBitmap.getHeight() / 2;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(0xFFFF0000);
        shadowPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先绘制阴阳
        canvas.drawBitmap(shadowBitmap, x, y, shadowPaint);

        //再绘制位图
        canvas.drawBitmap(srcBitmap, x, y, null);
    }
}
