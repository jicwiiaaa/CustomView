package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/21.
 */

public class BitmapMeshView extends View {
    private final static int WIDTH = 19;//横向网格数量
    private final static int HEIGHT = 19;//纵向网格数量
    private final static int count = (WIDTH + 1) * (HEIGHT + 1);//网格交点数量

    private Bitmap mBitmap;//位图资源
    private float[] verts;//交点坐标

    public BitmapMeshView(Context context) {
        this(context, null);
    }

    public BitmapMeshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //位图资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);

        //实例化数组
        verts = new float[count * 2];

        float segmentY = mBitmap.getHeight() / HEIGHT;
        float segmentX = mBitmap.getHeight() / WIDTH;
        int index = 0;
        for (int i = 0; i <= HEIGHT; i++) {
            float fy = segmentY * i;
//            for (int j = 0; j <= WIDTH; j++) {
//                float fx = segmentX * j + (HEIGHT - i) * 0.1F * segmentY;
//                setXY(fx, fy, index);
//                index ++;
//            }
            for (int x = 0; x <= WIDTH; x++) {
                float fx = segmentX * x;

                setXY(fx, fy, index);

                if (5 == i) {
                    if (8 == x) {
                        setXY(fx - segmentX, fy - segmentX, index);
                    }
                    if (9 == x) {
                        setXY(fx + segmentX, fy - segmentX, index);
                    }
                }
                if (6 == i) {
                    if (8 == x) {
                        setXY(fx - segmentX, fy + segmentX, index);
                    }
                    if (9 == x) {
                        setXY(fx + segmentX, fy + segmentX, index);
                    }
                }

                index += 1;
            }
        }

//        /*
//         * 生成各个交点坐标
//         */
//        int index = 0;
//        float multiple = mBitmap.getWidth();
//        for (int y = 0; y <= HEIGHT; y++) {
//            float fy = mBitmap.getHeight() * y / HEIGHT;
//            for (int x = 0; x <= WIDTH; x++) {
//                float fx = mBitmap.getWidth() * x / WIDTH + ((HEIGHT - y) * 1.0F / HEIGHT * multiple);
//                setXY(fx, fy, index);
//                int z = x + y;
//                index += 1;
//            }
//        }

    }

    /**
     * 将计算后的值放入坐标数组
     *
     * @param fx
     * @param fy
     * @param index
     */
    private void setXY(float fx, float fy, int index) {
        verts[index * 2 + 0] = fx;
        verts[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }
}
