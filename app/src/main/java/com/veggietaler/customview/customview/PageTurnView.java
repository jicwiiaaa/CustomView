package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuliu on 2017/6/22.
 */

public class PageTurnView extends View {
    private static final float TEXT_SIZE_NORMAL = 1 / 40f;//标准文字尺寸
    private static final float TEXT_SIZE_LARGE = 1 / 20f;//大号文字尺寸

    private TextPaint mTextPaint;//文本画笔
    private Context mContext;//上下文引用
    private List<Bitmap> mBitmaps;//位图数据列表
    private int pageIndex;//当前显示mBitmaps数据的下标
    private int mViewWidth, mViewHeight;//控件宽高
    private float mTextSizeNormal, mTextSizeLarger;//标准文字和大号文字尺寸
    private float mClipX;//裁剪右端点坐标
    private float mAutoAreaLeft;//控件左右两侧自动吸附的区域
    private float mAutoAreaRight;
    private float mCurPointX;//指尖触碰屏幕时点X的坐标值
    private float mMoveValid;//移动事件的有效距离

    private boolean isNextPage, isLastPage;//是否该显示下一页，是否最后一页的标识

    public PageTurnView(Context context) {
        this(context, null);
    }

    public PageTurnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 每次触发TouchEvent重置isNextPage为true
        isNextPage = true;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case  MotionEvent.ACTION_DOWN://触摸屏幕时
                //获取当前事件点x坐标
                mCurPointX = event.getX();

                /**
                 * 如果事件点落在回滚区域
                 */
                if(mCurPointX < mAutoAreaLeft){
                    //那就不翻下一页而是回上一页
                    isNextPage = false;
                    pageIndex --;
                    mClipX = mCurPointX;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE://滑动时
                float slideDis = event.getX() - mCurPointX;
                if (Math.abs(slideDis) > mMoveValid) {
                    //获取触摸点的坐标
                    mClipX = event.getX();
                    
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP://触摸抬起时
                //判断是否需要自动化东
                judgeSlideAuto();

                if (!isLastPage && isNextPage && mClipX <= 0) {
                    pageIndex ++;
                    mClipX = mViewWidth;
                    invalidate();
                }
                break;
        }
        return true;
    }

    /**
     * 判断是否需要自动滑动
     * 根据参数的当前值判断自动滑动
     */
    private void judgeSlideAuto() {
        //如果裁剪的右端点坐标在控件左端五分之一的区域内，那么我们直接让其自动滑到控件左端
        if(mClipX < mAutoAreaLeft){
            while (mClipX > 0) {
                mClipX --;
                invalidate();
            }
        }
        //如果裁剪的右端点坐标在控件右端五分之一的区域内，那么我们直接让其自动滑到控件右端
        if (mClipX > mAutoAreaRight) {
            while (mClipX < mViewWidth) {
                mClipX++;
                invalidate();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //获取控件宽高
        mViewWidth = w;
        mViewHeight = h;

        //初始化位图数据
        initBitmaps();

        //计算文字尺寸
        mTextSizeNormal = TEXT_SIZE_NORMAL * mViewHeight;
        mTextSizeLarger = TEXT_SIZE_LARGE * mViewHeight;

        //初始化裁剪右端点坐标
        mClipX = mViewWidth;

        //计算控件左侧和右侧自动吸附的区域
        mAutoAreaLeft = mViewWidth / 5F;
        mAutoAreaRight = mViewWidth * 4 / 5F;

        //计算移动的有效距离
        mMoveValid = mViewWidth / 100F;
    }

    /**
     * 初始化位图数据
     * 缩放位图尺寸与屏幕匹配
     */
    private void initBitmaps() {
        if (mBitmaps == null || mBitmaps.size() == 0) {
            return;
        }
        List<Bitmap> temp = new ArrayList<>();
        for (int i = mBitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i), mViewWidth, mViewHeight, true);
            temp.add(bitmap);
        }
        mBitmaps = temp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mBitmaps || mBitmaps.size() == 0) {
            defaultDisplay(canvas);
            return;
        }

        //绘制位图
        drawBitmaps(canvas);
    }

    /**
     * 没有数据时默认显示
     *
     * @param canvas
     */
    private void defaultDisplay(Canvas canvas) {
        //绘制底色
        canvas.drawColor(Color.WHITE);

        // 绘制标题文本
        mTextPaint.setTextSize(mTextSizeLarger);
        mTextPaint.setColor(Color.RED);
        canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4, mTextPaint);

        //绘制提示文本
        mTextPaint.setTextSize(mTextSizeNormal);
        mTextPaint.setColor(Color.BLACK);
        canvas.drawText("Please set data by using setBitmaps method", mViewWidth / 2, mViewHeight / 3, mTextPaint);
    }


    /**
     * 绘制位图
     *
     * @param canvas
     */
    private void drawBitmaps(Canvas canvas) {
        //绘制位图前重置isLastPage为false
        isLastPage = false;

        //限制pageIndex的值范围
        pageIndex = pageIndex < 0 ? 0 : pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

        //计算数据起始位置
        int start = mBitmaps.size() - 2 - pageIndex;
        int end = mBitmaps.size() - pageIndex;

        //如果数据起点位置小于0则表示当前已经到了最后一张图片
        if (start < 0) {
            //此时设置isLastPage为true
            isLastPage = true;

            //并显示提示信息
            showToast("This is last page");

            //强制重置起始位置
            start = 0;
            end = 1;
        }

        for (int i = 0; i < end; i++) {
            canvas.save();

            if (!isLastPage && i == end - 1) {
                canvas.clipRect(0, 0, mClipX, mViewHeight);
            }

            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);
        }

    }

    /**
     * 设置位图数据
     * @param bitmaps
     */
    public synchronized void setBitmaps(List<Bitmap> bitmaps){
        /**
         * 数据为空时抛出异常
         */
        if (null == bitmaps || bitmaps.size() == 0) {
            throw new IllegalArgumentException("no bitmap to display");
        }

        if (bitmaps.size() < 2) {
            throw new IllegalArgumentException("fuck u and fucking to use ImageView");
        }

        mBitmaps = bitmaps;
        invalidate();

    }

    /**
     * 吐司
     *
     * @param s
     */
    private void showToast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }
}
