package com.moon.beautygirlkotlin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;

import com.moon.beautygirlkotlin.R;

import androidx.annotation.NonNull;


/**
 * author: jiangtao.liang
 * date:   On 2019/8/7 16:29
 */
public class RoundedBackgroundSpan extends ReplacementSpan {

    private Context mContext;
    private int mBgColorResId; //Icon背景颜色

    private String mText;  //Icon内文字
    private float mBgHeight;  //Icon背景高度
    private float mBgWidth;  //Icon背景宽度
    private float mRadius;  //Icon圆角半径
    private float mRightMargin; //右边距
    private float mTextSize; //文字大小
    private int mTextColorResId; //文字颜色

    public RoundedBackgroundSpan(Context context, int bgColorResId) {
        //初始化默认数值
        initDefaultValue(context, bgColorResId);
        //计算背景的宽度
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        Paint mBgPaint = new Paint();
        mBgPaint.setColor(mContext.getResources().getColor(mBgColorResId));
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        //初始化文字画笔
        //icon文字画笔
        Paint mTextPaint = new TextPaint();
        mTextPaint.setColor(mContext.getResources().getColor(mTextColorResId));
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void initDefaultValue(Context context, int bgColorResId) {
        this.mContext = context.getApplicationContext();
        this.mBgColorResId = bgColorResId;
        this.mBgHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18f, mContext.getResources().getDisplayMetrics());
        this.mRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mContext.getResources().getDisplayMetrics());
        this.mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getResources().getDisplayMetrics());
        this.mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mContext.getResources().getDisplayMetrics());
        this.mTextColorResId = R.color.white;
    }

    public void setSpanText(String mText) {
        this.mText = mText;
        this.mBgWidth = caculateBgWidth(mText);
    }

    /**
     * 计算icon背景宽度
     */
    private float caculateBgWidth(String text) {
        if (text.length() > 1) {
            Rect textRect = new Rect();
            Paint paint = new Paint();
            paint.setTextSize(mTextSize);
            paint.getTextBounds(text, 0, text.length(), textRect);
            float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mContext.getResources().getDisplayMetrics());
            return textRect.width() + padding * 2;
        } else {
            //单字，宽高一致为正方形
            return mBgHeight;
        }
    }

    /**
     * 设置宽度，宽度=背景宽度+右边距
     */
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (mBgWidth + mRightMargin);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //画背景
        Paint bgPaint = new Paint();
        bgPaint.setColor(mContext.getResources().getColor(mBgColorResId));
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        Paint.FontMetrics metrics = paint.getFontMetrics();

        float textHeight = metrics.descent - metrics.ascent;
        //算出背景开始画的y坐标
        float bgStartY = y + (textHeight - mBgHeight) / 2 + metrics.ascent;

        //画背景
        RectF bgRect = new RectF(x, bgStartY, x + mBgWidth, bgStartY + mBgHeight);
        canvas.drawRoundRect(bgRect, mRadius, mRadius, bgPaint);

        //把字画在背景中间
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(mContext.getResources().getColor(mTextColorResId));
        textPaint.setTextSize(mTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);  //这个只针对x有效
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textRectHeight = fontMetrics.bottom - fontMetrics.top;
        canvas.drawText(mText, x + mBgWidth / 2, bgStartY + (mBgHeight - textRectHeight) / 2 - fontMetrics.top, textPaint);
    }
}
