package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by liuliu on 2017/6/19.
 */

public class MaskFilterView extends View {
    private final static int RECT_SIZE = 400;
    private final static int SPACE_SIZE = 20;
    private Paint mPaint;
    private int screenHeight, screenWidth;
    private int left, top, right, bottom;
    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initRes(context);
        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
    }

    /**
     * 初始化资源及数据
     * @param context
     */
    private void initRes(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;            //屏幕宽度
        screenHeight = metrics.heightPixels;        //屏幕高

        left = screenWidth / 2 - RECT_SIZE - SPACE_SIZE;
        top = screenHeight / 2 - RECT_SIZE - SPACE_SIZE;
        right = screenWidth / 2 - SPACE_SIZE;
        bottom = screenHeight / 2 - SPACE_SIZE;

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawRect(left, top, right, bottom, mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.drawRect(left + RECT_SIZE + SPACE_SIZE * 2, top, right + RECT_SIZE + SPACE_SIZE * 2, bottom, mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        canvas.drawRect(left, top + RECT_SIZE + SPACE_SIZE * 2, right, bottom + RECT_SIZE + SPACE_SIZE * 2, mPaint);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.drawRect(left + RECT_SIZE + SPACE_SIZE * 2, top + RECT_SIZE + SPACE_SIZE * 2, right + RECT_SIZE + SPACE_SIZE * 2, bottom + RECT_SIZE + SPACE_SIZE * 2, mPaint);

    }
}
