package com.veggietaler.customview.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/20.
 */

public class MatrixImageView extends ImageView {
    private final static int MODE_NONE = 1;
    private final static int MODE_DRAG = 2;
    private final static int MODE_ZOOM = 3;

    private int mode;//当前模式

    private PointF start;
    //    private PointF mid;
    private float saveScale;
    private float saveRotate;

    private Matrix currentMatrix, saveMatrix;//当前和保存了的Matrix对象

    public MatrixImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }

    private void init(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;            //屏幕宽度
        int screenHeight = metrics.heightPixels;        //屏幕高

        currentMatrix = new Matrix();
        saveMatrix = new Matrix();
        start = new PointF();

        mode = MODE_NONE;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);
        setImageBitmap(bitmap);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                saveMatrix.set(currentMatrix);
                start.set(event.getX(), event.getY());
                mode = MODE_DRAG;

                break;
            case MotionEvent.ACTION_POINTER_DOWN://第二根手指按下
                saveScale = calSpace(event);
                if (saveScale > 10) {
                    mode = MODE_ZOOM;
                    saveMatrix.set(currentMatrix);
                }
                saveRotate = calRotate(event);

                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG) {
                    currentMatrix.set(saveMatrix);
                    float x = event.getX() - start.x;
                    float y = event.getY() - start.y;
                    currentMatrix.postTranslate(x, y);
                } else if (mode == MODE_ZOOM) {
                    currentMatrix.set(saveMatrix);
                    float currentScale = calSpace(event);
                    /* *
                    * 指尖移动距离大于10F缩放
                    */
                    if (currentScale > 10) {
                        float scale = currentScale / saveScale;
                        currentMatrix.postScale(scale, scale);
                    }

                    //旋转
                    float currentRotate = calRotate(event);
                    currentMatrix.postRotate(currentRotate - saveRotate, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = MODE_NONE;
                break;
        }
        setImageMatrix(currentMatrix);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
         super.onDraw(canvas);
    }

    private float calRotate(MotionEvent event) {
        double x = event.getX(0) - event.getX(1);
        double y = event.getY(0) - event.getY(1);
        double radius = Math.atan2(x, y);
        return (float) Math.toDegrees(radius);
    }

    //计算两指之间的距离
    private float calSpace(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
