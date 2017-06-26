package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2017/6/19.
 */

public class PathEffectView extends View {
    private Paint mPaint;//画笔
    private Path mPath;//路径对象
    private PathEffect[] mEffects;//特效路径数组
    private int mPhase;//偏移值
    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        initPath();
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(0, 0);

        for (int i = 0; i < 30; i++) {
            mPath.lineTo(35 * i, (float) (Math.random() * 100));
        }

        mEffects = new PathEffect[7];
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(0xFFFF0000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制各种path效果
        mEffects[0] = null;
        mEffects[1] = new CornerPathEffect(10);
        mEffects[2] = new DiscretePathEffect(6.0f, 10.0f);
        mEffects[3] = new DashPathEffect(new float[]{10, 20}, mPhase);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        mEffects[4] = new PathDashPathEffect(path, 12, mPhase, PathDashPathEffect.Style.ROTATE);
        mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
        mEffects[6] = new SumPathEffect(mEffects[2], mEffects[4]);

        for (int i = 0; i < mEffects.length; i++) {
            mPaint.setPathEffect(mEffects[i]);
            canvas.drawPath(mPath, mPaint);

            // 每绘制一条将画布向下平移220个像素
            canvas.translate(0, 220);
        }

        //刷新偏移值并重绘视图实现动态效果
        mPhase++;
        invalidate();
    }
}
