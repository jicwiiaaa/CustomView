package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2017/6/21.
 */

public class WaveView extends View {

    private Paint mPaint;
    private Path mPath1;
    private Path mPath2;

    private float controlPointX1;//二阶贝塞尔曲线控制点
    private float controlPointY1;
    private float controlPointX2;//第二条二阶贝塞尔曲线控制点
    private float controlPointY2;

    private int viewWidth;//控件宽度
    private int viewHeight;//控件高度
    private float waveY;//wavepath两边端点的高度
    private boolean isRunLeft1 = false;
    private boolean isRunLeft2 = false;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFA2D6AE);

        mPath1 = new Path();
        mPath2 = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = w;
        viewHeight = h;

        controlPointX1 = 0;
        controlPointX2 = viewWidth / 2;
        controlPointY1 = -1 / 16F * viewHeight;
        controlPointY2 = -3 / 32F * viewHeight;
        waveY = viewHeight / 8;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath1.reset();
        mPath1.moveTo(- viewWidth / 4, waveY);//二阶曲线开始和结束点都放在屏幕外
        mPath1.quadTo(controlPointX1, controlPointY1, viewWidth + viewWidth / 4, waveY);
        mPath1.lineTo(viewWidth + viewWidth / 4, viewHeight);
        mPath1.lineTo(- viewWidth / 4, viewHeight);
        mPath1.close();
        mPaint.setColor(0xFFA2D6AE);
        canvas.drawPath(mPath1, mPaint);

        mPath2.reset();
        mPath2.moveTo(- viewWidth / 4, waveY);//二阶曲线开始和结束点都放在屏幕外
        mPath2.quadTo(controlPointX2, controlPointY2, viewWidth + viewWidth / 4, waveY);
        mPath2.lineTo(viewWidth + viewWidth / 4, viewHeight);
        mPath2.lineTo(- viewWidth / 4, viewHeight);
        mPath2.close();
        mPaint.setColor(0x99B3F6BF);
        canvas.drawPath(mPath2, mPaint);

        if (controlPointX1 <= - viewWidth / 4) {
            isRunLeft1 = false;
        }
        if (controlPointX1 >= viewWidth + viewWidth / 4) {
            isRunLeft1 = true;
        }

        if (controlPointX2 <= - viewWidth / 4) {
            isRunLeft2 = false;
        }
        if (controlPointX2 >= viewWidth + viewWidth / 4) {
            isRunLeft2 = true;
        }

        controlPointX1 = isRunLeft1 ? controlPointX1 - 20 : controlPointX1 + 20;
        controlPointX2 = isRunLeft2 ? controlPointX2 - 20 : controlPointX2 + 20;

        if (controlPointY2 <= viewHeight * 3 / 4) {
            controlPointY1 += 2;
            controlPointY2 += 2;
            waveY += 2;
        }

        invalidate();

    }
}
