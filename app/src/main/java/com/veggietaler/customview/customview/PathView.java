package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuliu on 2017/6/21.
 */

public class PathView extends View {
    private Paint mPaint;
    private Paint mPaintText;
    private Path mPath;

    private float touchX, touchY;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.CYAN);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintText.setTextSize(30);
        mPaintText.setColor(Color.DKGRAY);

        mPath = new Path();
//        mPath.moveTo(100, 100);
//        mPath.lineTo(500, 500);
//        mPath.lineTo(300, 500);
//        mPath.close();
//
//        mPath.quadTo(400, 600, 800, 600);
        mPath.moveTo(100, 100);
        RectF oval = new RectF(300, 600, 600, 1000);
//        mPath.addArc(oval, 0, 90);
        mPath.addOval(oval, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(100, 100, 200, 200, mPaint);
//        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mPath, mPaint);
        canvas.drawTextOnPath("的内功的那个绿色的你说呢顾客索尼的功能类似的那个", mPath, 0, 0, mPaintText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        mPath.reset();
        mPath.moveTo(100, 100);
//        mPath.quadTo(touchX, touchY, 800, 600);//二阶阶贝塞尔曲线（一阶是直线，无控制点）
        mPath.cubicTo(300, 100, touchX, touchY, 800, 600);//三阶贝塞尔曲线

        invalidate();
        return true;
    }
}
