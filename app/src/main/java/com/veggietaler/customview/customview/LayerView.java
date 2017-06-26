package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.veggietaler.customview.R;

/**
 * Created by liuliu on 2017/6/22.
 */

public class LayerView extends View {
    private Bitmap mBitmap;
    private int mViewWidth;
    private int mViewHeight;
    public LayerView(Context context) {
        this(context, null);
    }

    public LayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;

        mBitmap = Bitmap.createScaledBitmap(mBitmap, mViewWidth, mViewHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
//        canvas.scale(0.5f, 0.5f, mViewWidth, 0);
        canvas.skew(0.5f, 0.5f);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }
}
