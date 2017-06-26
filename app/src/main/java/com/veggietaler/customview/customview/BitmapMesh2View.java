package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/21.
 */

public class BitmapMesh2View extends View {
    private final static int WIDTH = 10;//横向网格数量
    private final static int HEIGHT = 10;//纵向网格数量
    private final static int count = (WIDTH + 1) * (HEIGHT + 1);//网格交点数量

    private Bitmap mBitmap;//位图资源
    private float[] matrixOriginals;//原始坐标点
    private float[] matrixMoveds;//移动后的坐标点

    private Paint mPaintOriginal, mPaintMoved, mPaintLine;
    private float clickX, clickY;//手势触摸点

    public BitmapMesh2View(Context context) {
        this(context, null);
    }

    public BitmapMesh2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        //位图资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty2);

        //实例化数组
        matrixOriginals = new float[count * 2];
        matrixMoveds = new float[count * 2];

        float segmentY = mBitmap.getHeight() / HEIGHT;
        float segmentX = mBitmap.getHeight() / WIDTH;
        int index = 0;
        for (int i = 0; i <= HEIGHT; i++) {
            float fy = segmentY * i;
            for (int j = 0; j <= WIDTH; j++) {
                float fx = segmentX * j;
                setXY(matrixOriginals, fx, fy, index);
                setXY(matrixMoveds, fx, fy, index);
                index ++;
            }
        }


    }

    private void initPaint() {
        mPaintOriginal = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOriginal.setColor(0x660000FF);
        mPaintMoved = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintMoved.setColor(0x99FF0000);
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(0x660000FF);
    }

    /**
     * 将计算后的值放入坐标数组
     *
     * @param matrixMoveds
     * @param fx
     * @param fy
     * @param index
     */
    private void setXY(float[] matrixMoveds, float fx, float fy, int index) {
        matrixMoveds[index * 2 + 0] = fx;
        matrixMoveds[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, matrixMoveds, 0, null, 0, null);

        /**
         * 画辅助线
         */
        drawGuides(canvas);
    }

    private void drawGuides(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            float fx = matrixOriginals[i * 2 + 0];
            float fy = matrixOriginals[i * 2 + 1];
            canvas.drawCircle(fx, fy, 4, mPaintOriginal);

            float fx1 = matrixMoveds[i * 2 + 0];
            float fy1 = matrixMoveds[i * 2 + 1];
            canvas.drawCircle(fx1, fy1, 4, mPaintMoved);

            canvas.drawLine(fx, fy, fx1, fy1, mPaintOriginal);
        }

        canvas.drawCircle(clickX, clickY, 8, mPaintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        smudge();
        invalidate();
        return true;
    }

    private void smudge() {
        for (int i = 0; i < count * 2; i += 2) {

            float xOriginal = matrixOriginals[i + 0];
            float yOriginal = matrixOriginals[i + 1];

            float dist_click_to_origin_x = clickX - xOriginal;
            float dist_click_to_origin_y = clickY - yOriginal;

            float kv_kat = dist_click_to_origin_x * dist_click_to_origin_x + dist_click_to_origin_y * dist_click_to_origin_y;

            float pull = (float) (1000000 / kv_kat / Math.sqrt(kv_kat));

            if (pull >= 1) {
                matrixMoveds[i + 0] = clickX;
                matrixMoveds[i + 1] = clickY;
            } else {
                matrixMoveds[i + 0] = xOriginal + dist_click_to_origin_x * pull;
                matrixMoveds[i + 1] = yOriginal + dist_click_to_origin_y * pull;
            }
        }
    }
}
