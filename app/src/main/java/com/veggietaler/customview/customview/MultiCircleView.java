package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2017/6/20.
 */

public class MultiCircleView extends View {
    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
            SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比
            LINE_LENGTH = 3F / 32F, // 线段长度占比
            CRICLE_LARGER_RADIU = 3F / 32F,// 大圆半径
            CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径
            ARC_RADIU = 1F / 8F,// 弧半径
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

    private Paint mPaintLine;
    private Paint mPaintText;
    private Paint mPaintArc;
    private int size;
    private float ccX, ccY;//中心园的坐标
    private float largeCircleRadius;
    private float lineLength;
    private float space;
    private float textOffsetY;//文本y轴baseline偏移值
    

    public MultiCircleView(Context context) {
        this(context, null);
    }

    public MultiCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStyle(Paint.Style.STROKE);
//        mPaintLine.setStrokeWidth(STROKE_WIDTH * size);
        mPaintLine.setStrokeCap(Paint.Cap.ROUND);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintText.setTextSize(30);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        textOffsetY = (mPaintText.ascent() + mPaintText.descent()) / 2;

        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 强制长宽一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        size = w;

        mPaintLine.setStrokeWidth(STROKE_WIDTH * size);
        largeCircleRadius = CRICLE_LARGER_RADIU * size;
        lineLength = LINE_LENGTH * size;
        space = SPACE * size;

        ccX = size / 2;
        ccY = size / 2 + largeCircleRadius;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制背景
        canvas.drawColor(0xFFF29B76);

        //绘制中心园
        canvas.drawCircle(ccX, ccY, largeCircleRadius, mPaintLine);
        canvas.drawText("CustomView", ccX, ccY - textOffsetY, mPaintText);

        //绘制左上角图形
        drawLeftTop(canvas);

        //绘制右上角图形
        drawRightTop(canvas);

        //绘制左下角图形
        drawLeftBottom(canvas);

        //绘制右下角图形
        drawRightBottom(canvas);

        //绘制右下角图形
        drawBottom(canvas);
    }

    private void drawBottom(Canvas canvas) {
        //锁定画布
        canvas.save();
        //平移画布
        canvas.translate(ccX, ccY);

        //线-圆
        canvas.drawLine(0, lineLength + space, 0, lineLength * 2 + space, mPaintLine);
        canvas.drawCircle(0, lineLength + largeCircleRadius * 2 + space * 2, largeCircleRadius, mPaintLine);

        canvas.restore();
    }

    private void drawRightBottom(Canvas canvas) {
        //锁定画布
        canvas.save();
        //平移旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(105);

        //线-圆
        canvas.drawLine(0, -lineLength - space, 0, -lineLength * 2 - space, mPaintLine);
        canvas.drawCircle(0, -lineLength - largeCircleRadius * 2 - space * 2, largeCircleRadius, mPaintLine);

        canvas.restore();
    }

    private void drawLeftBottom(Canvas canvas) {
        //锁定画布
        canvas.save();
        //平移旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-105);

        //线-圆
        canvas.drawLine(0, -lineLength - space, 0, -lineLength * 2 - space, mPaintLine);
        canvas.drawCircle(0, -lineLength - largeCircleRadius * 2 - space * 2, largeCircleRadius, mPaintLine);

        canvas.restore();
    }

    private void drawRightTop(Canvas canvas) {
        //锁定画布
        canvas.save();
        //平移旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(30);

        //线-圆
        canvas.drawLine(0, -lineLength, 0, -lineLength * 2, mPaintLine);
        canvas.drawCircle(0, -lineLength - largeCircleRadius * 2, largeCircleRadius, mPaintLine);

        drawRightTopArc(canvas, -lineLength - largeCircleRadius * 2);

        canvas.restore();
    }

    private void drawRightTopArc(Canvas canvas, float arcY) {
        canvas.save();
        canvas.translate(0, arcY);
        canvas.rotate(-30);

        float arcRadius = ARC_RADIU * size;
        RectF oval = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        mPaintArc.setStyle(Paint.Style.FILL);
        mPaintArc.setColor(0x55EC6941);
        canvas.drawArc(oval, -22.5f, -135, true, mPaintArc);
        mPaintArc.setStyle(Paint.Style.STROKE);
        mPaintArc.setColor(Color.WHITE);
        mPaintArc.setStrokeWidth(STROKE_WIDTH * size);
        canvas.drawArc(oval, -22.5f, -135, false, mPaintArc);

        canvas.save();
        canvas.rotate(- 135/2);

        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate(i * 135 / 4);

            canvas.drawText("Menu", 0, -ARC_TEXT_RADIU * size, mPaintText);
            canvas.restore();
        }

        canvas.restore();


        canvas.restore();
    }


    private void drawLeftTop(Canvas canvas) {
        //锁定画布
        canvas.save();
        //平移旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-30);

        //线-圆-线-圆
        canvas.drawLine(0, -lineLength, 0, -lineLength * 2, mPaintLine);
        canvas.drawCircle(0, -lineLength - largeCircleRadius * 2, largeCircleRadius, mPaintLine);
        canvas.drawLine(0, -lineLength - largeCircleRadius * 3, 0, -lineLength * 2 - largeCircleRadius * 3, mPaintLine);
        canvas.drawCircle(0, -lineLength * 2 - largeCircleRadius * 4, largeCircleRadius, mPaintLine);
        {//调整画布，修改文字方向
            canvas.save();
            canvas.translate(0, -lineLength - largeCircleRadius * 2 - textOffsetY);
            canvas.rotate(30);
            canvas.drawText("Apple", 0, 0, mPaintText);
            canvas.restore();
        }

        canvas.restore();
    }
}
