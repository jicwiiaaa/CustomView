package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * 自定义缩放圆形view
 * Created by liuliu on 2017/6/16.
 */

public class ZoomCircleView extends View implements Runnable {
    private Paint mPaint;//画笔
    private Context mContext;//上下文引用
    private int screenHeight;//屏幕高度
    private int screenWidth;//屏幕宽度
    private int radius;//半径

    public ZoomCircleView(Context context) {
        this(context, null);
    }

    public ZoomCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initPaint();

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;            //屏幕宽度
        screenHeight = metrics.heightPixels;        //屏幕高度
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //实例化paint对象并开启抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /**
         * 画笔样式分三种：
         * Paint.Style.STROKE 描边
         * Paint.Style.FILL_AND_STROKE 描边并填充
         * Paint.Style.FILL 填充
         */
        mPaint.setStyle(Paint.Style.STROKE);

        /**
         * 设置描边粗细 单位：px
         * ps：setStrokeWidth(0)时，描边宽度不为0，而是1
         */
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(screenWidth / 2, screenHeight / 3 , radius, mPaint);

        if (radius < 400) {
            radius += 10;
        } else {
            radius = 0;
        }
        invalidate();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (radius < 400) {
                    radius += 10;
                    //非UI线程更新view
                    postInvalidate();
                } else {
                    radius = 0;
                }
                Thread.sleep(40);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
