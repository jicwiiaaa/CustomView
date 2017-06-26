package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liuliu on 2017/6/21.
 */

public class LineChartView extends View {
    private Paint mPaintCoordinate;//坐标线画笔
    private Paint mPaintLine;//数据折线画笔
    private Paint mPaintGrid;//网格线画笔
    private Paint mPaintTextCoordinate;//横纵坐标文本说明画笔
    private Paint mPaintTextScale;//横纵坐标刻度文本画笔

    private Path mPath;//折线path对象
    private float xPointX, xPointY;//横坐标说明文字的位置
    private float yPointX, yPointY;//纵坐标说明文字的位置

    private int viewSize;
    private float viewLeft, viewTop, viewRight, viewBottom;

    private String xLabel = "x轴";
    private String yLabel = "y轴";

    private List<PointF> pointFs = new ArrayList<>();//数据坐标
    private int maxX, maxY;//横纵坐标最大值
    private float spaceX, spaceY;//横纵轴坐标间隔
    private float space;//定义一个距离大小


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        
        initData();

    }

    private void initData() {
        Random random = new Random();
        pointFs = new ArrayList<PointF>();
        for (int i = 0; i < 8; i++) {
            PointF pointF = new PointF();
            pointF.x = (float) (random.nextInt(100) * i);
            pointF.y = (float) (random.nextInt(100) * i);

            pointFs.add(pointF);
        }
    }

    private void initPaint() {
        mPaintCoordinate = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintCoordinate.setColor(Color.WHITE);
        mPaintCoordinate.setStyle(Paint.Style.STROKE);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setPathEffect(new CornerPathEffect(25));

        mPaintGrid = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintGrid.setColor(Color.WHITE);
        mPaintGrid.setStyle(Paint.Style.STROKE);

        mPaintTextCoordinate = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintTextCoordinate.setColor(Color.WHITE);

        mPaintTextScale = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintTextScale.setColor(Color.WHITE);

        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //目前宽高强制一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewSize = w;

        viewLeft = viewSize / 16;
        viewTop = viewSize / 16;
        viewRight = viewSize * 15 / 16;
        viewBottom = viewSize * 7 / 8;

        mPaintCoordinate.setStrokeWidth(viewSize  / 128);
        mPaintLine.setStrokeWidth(viewSize / 128);
        mPaintGrid.setStrokeWidth(viewSize / 512);
        mPaintTextCoordinate.setTextSize(viewSize / 32);
        mPaintTextScale.setTextSize(viewSize * 3 / 128);

        xPointX = viewSize * 3 / 32;
        xPointY = viewSize / 16;
        yPointX = viewSize * 7 / 8;
        yPointY = viewSize * 15 / 16;

        space = viewSize / 128;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xffcd782a);

        //画坐标轴
        drawCoordinates(canvas);

        //画网格线
        drawGrid(canvas);

        //画数据线
        drawDataLine(canvas);
    }

    private void drawDataLine(Canvas canvas) {



        for (int i = 0; i < pointFs.size(); i++) {
            float pointX = pointFs.get(i).x / maxX * ((viewRight - viewLeft) * (pointFs.size() - 1) / pointFs.size()) + viewLeft;
            float pointY = viewBottom - pointFs.get(i).y / maxY * ((viewBottom - viewTop) * (pointFs.size() - 1) / pointFs.size());

            if (i == 0) {
                mPath.moveTo(pointX, pointY);
            }else {
                mPath.lineTo(pointX, pointY);
            }
        }

        canvas.drawPath(mPath, mPaintLine);
    }

    private void drawGrid(Canvas canvas) {
        canvas.save();
        //计算最大横坐标
        maxX = 0;
        for (int i = 0; i < pointFs.size(); i++) {
            if (maxX < pointFs.get(i).x){
                maxX = (int) pointFs.get(i).x;
            }
        }
        //计算横轴最近能被pointFs整除的数
        int reminderX = maxX % (pointFs.size() - 1);
        maxX = reminderX == 0 ? maxX : maxX + (pointFs.size() - 1) - reminderX;

        //计算最大纵坐标
        maxY = 0;
        for (int i = 0; i < pointFs.size(); i++) {
            if (maxY < pointFs.get(i).y){
                maxY = (int) pointFs.get(i).y;
            }
        }
        //计算纵轴最近能被pointFs整除的数
        int reminderY = maxY % (pointFs.size() - 1);
        maxY = reminderY == 0 ? maxY : maxY + (pointFs.size() - 1) - reminderY;

        spaceX = (viewRight - viewLeft) / pointFs.size();
        spaceY = (viewBottom - viewTop) / pointFs.size();

        int sc = canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 75, Canvas.ALL_SAVE_FLAG);
        //画横网格线
        for (float y = viewBottom - spaceY; y - viewTop >= 1; y -= spaceY){//y - viewTop >= 1,算余数时，会有小数偏差，+1来抵消
            canvas.drawLine(viewLeft, y, viewRight - spaceX, y, mPaintLine);
        }
        //画纵网格线
        for (float x = viewLeft + spaceX; x + 1 < viewRight; x += spaceX){//x + 1 < viewRight,算余数时，会有小数偏差，+1来抵消
            canvas.drawLine(x, viewBottom, x, viewTop + spaceY, mPaintLine);
        }
        canvas.restoreToCount(sc);

        //画横坐标刻度
        mPaintTextScale.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < pointFs.size(); i++) {
            canvas.drawText(maxX / (pointFs.size() - 1) * i + "", viewLeft + spaceX * i, viewBottom - mPaintTextScale.ascent() + space, mPaintTextScale);
        }

        //或纵坐标刻度
        mPaintTextScale.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < pointFs.size(); i++) {
            canvas.drawText(maxY / (pointFs.size() - 1) * i + "", viewLeft - space, viewBottom - spaceY * i, mPaintTextScale);
        }



        canvas.restore();
    }

    private void drawCoordinates(Canvas canvas) {
        canvas.save();
        canvas.drawLine(viewLeft, viewTop, viewLeft, viewBottom, mPaintCoordinate);
        canvas.drawLine(viewLeft, viewBottom, viewRight, viewBottom, mPaintCoordinate);
        canvas.drawText(xLabel, xPointX, xPointY, mPaintTextCoordinate);
        canvas.drawText(yLabel, yPointX, yPointY, mPaintTextCoordinate);
        canvas.restore();
    }


    public synchronized void setData(List<PointF> pointFs, String labelX, String labelY){
        this.pointFs = pointFs;
        this.xLabel = labelX;
        this.yLabel = labelY;
        invalidate();
    }
}
