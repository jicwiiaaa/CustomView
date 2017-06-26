package com.veggietaler.customview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 *
 * Created by liuliu on 2017/6/16.
 */

public class FontView extends View {

    private final static String TAG = "FontView";
    private final static String TEXT = "ap刘德华ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
//    private Paint mPaint;//画笔
    private Paint.FontMetrics mFontMetrics;//文本测量对象
    private Paint paintText1;//文本画笔
    private Paint paintText2;//文本画笔
    private Paint paintText3;//文本画笔
    private Paint paintText4;//文本画笔
    private Paint paintLine;//中心线画笔

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paintText1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText1.setTextSize(50);
        paintText1.setStrikeThruText(true);//文本删除线
        paintText1.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        paintText1.setColor(Color.MAGENTA);

        paintText2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText2.setTextSize(50);
        paintText2.setTextSkewX(-0.25f);
        paintText2.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        paintText2.setColor(Color.MAGENTA);

        paintText3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText3.setTextSize(50);
        paintText3.setTextScaleX(1.5f);
        paintText3.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL));
        paintText3.setColor(Color.MAGENTA);

        paintText4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText4.setTextSize(50);
        paintText4.setTextAlign(Paint.Align.CENTER);
        paintText4.setFakeBoldText(true);
        paintText4.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        paintText4.setColor(Color.MAGENTA);

        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(1);
        paintLine.setColor(Color.BLUE);

        mFontMetrics = paintText1.getFontMetrics();

        Log.d(TAG, "ascent-->" + mFontMetrics.ascent);
        Log.d(TAG, "ascent-->" + mFontMetrics.bottom);
        Log.d(TAG, "ascent-->" + mFontMetrics.descent);
        Log.d(TAG, "ascent-->" + mFontMetrics.top);
        Log.d(TAG, "ascent-->" + mFontMetrics.leading);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawText(TEXT, 0, Math.abs(mFontMetrics.top), mPaint);

        float baseX = (canvas.getWidth() - paintText1.measureText(TEXT)) / 2;
//        float baseY = canvas.getHeight() / 2 + Math.abs((paintText1.ascent()) / 2 + Math.abs(paintText1.descent()) / 2);//ascent，descent与文本内容无关，影响因素为textSize和typeface，所以这里可以直接通过paint拿到
//                                                                                                            //baseline=0,ascent<0,descent>0
        float textHeight = Math.abs(mFontMetrics.ascent) + Math.abs(mFontMetrics.descent) + Math.abs(mFontMetrics.leading);//此处为个人猜测
        float baseY = canvas.getHeight() / 2 - textHeight * 2 + Math.abs(mFontMetrics.leading) + Math.abs(mFontMetrics.ascent);

        canvas.drawText(TEXT, baseX, baseY, paintText1);
        canvas.drawText(TEXT, baseX, baseY + textHeight, paintText2);
        canvas.drawText(TEXT, baseX, baseY + textHeight * 2, paintText3);
        canvas.drawText(TEXT, baseX, baseY + textHeight * 3, paintText4);

        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, paintLine);
    }
}
