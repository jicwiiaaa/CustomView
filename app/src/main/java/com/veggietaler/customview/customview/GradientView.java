package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2017/6/19.
 */

public class GradientView extends View {
    private final static int RECT_SIZE = 200;
    private final static int TRANS_DISTANCE = 150;
    private Paint mPaint;
    private LinearGradient mlinearGradient1,mlinearGradient2;
    private SweepGradient mSweepGradient1, mSweepGradient2;
    private RadialGradient mRadialGradient1, mRadialGradient2;

    private float left, top, right, bottom;//第一个矩形的坐标

    public GradientView(Context context) {
        this(context, null);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initRes();
        initPaint();
    }

    private void initRes() {
        left = 0;
        top = 0;
        right = RECT_SIZE;
        bottom = RECT_SIZE;

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);


        mlinearGradient1 = new LinearGradient(left, top, right / 2, bottom / 2, Color.RED, Color.YELLOW, Shader.TileMode.MIRROR);
//        mlinearGradient2 = new LinearGradient(left + TRANS_DISTANCE, top + RECT_SIZE, right + TRANS_DISTANCE, bottom + RECT_SIZE, new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}, new float[]{0.1f, 0.6f, 0.7f, 0.8f, 0.9f}, Shader.TileMode.REPEAT);
        mlinearGradient2 = new LinearGradient(left + TRANS_DISTANCE, top + RECT_SIZE, right + TRANS_DISTANCE, bottom + RECT_SIZE, new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}, null, Shader.TileMode.REPEAT);

        mRadialGradient1 = new RadialGradient((left + right + TRANS_DISTANCE * 4) / 2, (top + bottom + RECT_SIZE * 4) / 2, 200, Color.RED, Color.YELLOW, Shader.TileMode.MIRROR);
        mRadialGradient2 = new RadialGradient((left + right + TRANS_DISTANCE * 6) / 2, (top + bottom + RECT_SIZE * 6) / 2, 200, new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}, null, Shader.TileMode.MIRROR);

        mSweepGradient1 = new SweepGradient((left + right + TRANS_DISTANCE * 8) / 2, (top + bottom + RECT_SIZE * 8) / 2, Color.RED, Color.YELLOW);
        mSweepGradient2 = new SweepGradient((left + right + TRANS_DISTANCE * 10) / 2, (top + bottom + RECT_SIZE * 10) / 2, new int[]{Color.RED, Color.YELLOW, Color.RED}, null);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mlinearGradient1);
        canvas.drawRect(left, top, right, bottom, mPaint);
        mPaint.setShader(mlinearGradient2);
        canvas.drawRect(left + TRANS_DISTANCE, top + RECT_SIZE, right + TRANS_DISTANCE, bottom + RECT_SIZE, mPaint);
        mPaint.setShader(mRadialGradient1);
        canvas.drawRect(left + TRANS_DISTANCE * 2, top + RECT_SIZE * 2, right + TRANS_DISTANCE * 2, bottom + RECT_SIZE * 2, mPaint);
        mPaint.setShader(mRadialGradient2);
        canvas.drawRect(left + TRANS_DISTANCE * 3, top + RECT_SIZE * 3, right + TRANS_DISTANCE * 3, bottom + RECT_SIZE * 3, mPaint);
        mPaint.setShader(mSweepGradient1);
        canvas.drawRect(left + TRANS_DISTANCE * 4, top + RECT_SIZE * 4, right + TRANS_DISTANCE * 4, bottom + RECT_SIZE * 4, mPaint);
        mPaint.setShader(mSweepGradient2);
        canvas.drawRect(left + TRANS_DISTANCE * 5, top + RECT_SIZE * 5, right + TRANS_DISTANCE * 5, bottom + RECT_SIZE * 5, mPaint);

    }
}
