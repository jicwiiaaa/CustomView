package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2017/6/21.
 */

public class CanvasView extends View {
    private Paint mPaint;
    private Rect mRect;
    private Path mPath;

    private Region regionA, regionB;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        mRect = new Rect(0, 0, 500, 500);

        mRect.union(250, 250, 750, 750);
//        mRect.intersect(250, 250, 750, 750);

        mPath = new Path();
        mPath.moveTo(200, 200);
        mPath.lineTo(500, 700);
        mPath.lineTo(300, 600);
        mPath.close();

        regionA = new Region(700, 700, 900, 900);
        regionB = new Region(800, 800, 1000, 1000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

//        canvas.clipRect(mRect);
        canvas.save();
        canvas.clipPath(mPath);
        canvas.drawColor(Color.RED);
        canvas.restore();


        canvas.save();
        canvas.clipRegion(regionA);
        canvas.clipRegion(regionB, Region.Op.UNION);
        canvas.drawColor(Color.RED);
        canvas.restore();

        canvas.drawRect(700, 700, 900, 900, mPaint);
        canvas.drawRect(800, 800, 1000, 1000, mPaint);

    }
}
