package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by liuliu on 2017/6/23.
 */

public class FoldView extends View {
    private static final float VALUE_ADDED = 1 / 500F;// 精度附加值占比
    private static final float BUFF_AREA = 1 / 50F;// 底部缓冲区域占比
    private static final float AUTO_AREA_BUTTOM_RIGHT = 3 / 4F, AUTO_AREA_BUTTOM_LEFT = 1 / 8F;// 右下角和左侧自滑区域占比
    private static final float AUTO_SLIDE_BL_V = 1 / 25F, AUTO_SLIDE_BR_V = 1 / 100F;// 滑动速度占比
    private static final float TEXT_SIZE_NORMAL = 1 / 40F, TEXT_SIZE_LARGER = 1 / 20F;// 标准文字尺寸和大号文字尺寸的占比

    private List<Bitmap> Bitmaps;//位图数据列表

    private SlideHandler mSlideHander;//滑动处理Handler
    private Paint mPaint;//画笔
    private TextPaint mTextPaint;//文本画笔
    private Context mContext;//上下文环境引用

    private Path mPath;//折叠路径
    private Path mPathFoldAndNext;//一个包含折叠和下一页区域的path

    private Region mRegionShortSize;//短边的有效区域
    private Region mRegionCurrent;//当前页区域，其实就是控件的大小

    private int mViewWidth, mViewHeight;//控件宽高
    private int mPageIndex;//当前显示mBitmaps数据的下标

    private float mPointx, mPointY;//手指触摸点的坐标
    private float mValueAdded;//精度附减值
    private float mBuffArea;//底部缓冲区域
    private float mAutoAreaButtom, mAutoAreaRight, mAutoAreaLeft;//右下角和左侧自滑区域

    private float mStart_X, mStart_Y;//直线起点坐标
    private float mAutoSlideV_BL, mAutoSlideV_BR;//滑动速度
    private float mTextSizeNormal, mTextSizeLarger;//标准文本尺寸和大号文本尺寸
    private float mDegrees;//当前Y边长与Y轴的夹角

    private boolean isSlide, isLastPage, isNextPage;//是否执行滑动，是否已经是最后一页，是否可显示下一页的标识符

    private Slide mSlide;//定义当前滑动是往做下滑还是右下滑
    private Ratio mRatio;//定义当前折叠边长

    /**
     * 枚举类定义滑动方向
     */
    private enum Slide {
        LEFT_BOTTOM, RIGHT_BOTTOM
    }

    /**
     * 枚举类定义长边短边
     */
    private enum Ratio {
        LONG, SHORT
    }


    public FoldView(Context context) {
        this(context, null);
    }

    public FoldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        /**
         * 实例化文本画笔并设置参数
         */
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        /**
         * 实例化画笔对象并设置参数
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        /**
         * 实例化路径对象
         */
        mPath = new Path();
        mPathFoldAndNext = new Path();

        /**
         * 实例化区域对象
         */
        mRegionCurrent = new Region();
        mRegionShortSize = new Region();

        //实例化滑动Handler处理器



    }

    /**
     * 计算滑动参数变化
     */
    private void slide(){
        /**
         * 如果滑动标识值为false则返回
         */
        if (!isSlide) {
            return;
        }


    }

    public class SlideHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //循环调用滑动计算
            FoldView.this.slide();

            //重绘视图
            FoldView.this.invalidate();
        }

        /**
         * 延迟向Handler发送消息实现时间间隔
         * @param delayMillis
         */
        public void sleep(long delayMillis){
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);

        }
    }
}
