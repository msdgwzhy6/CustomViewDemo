package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.onlyloveyd.customview.R;

/**
 * 文 件 名: ArcView
 * 创 建 人: 易冬
 * 创建日期: 2017/10/10 16:48
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 */
public class ArcView extends View {

    private final int MAX_SWEEP_ANGLE = 240;
    private final int START_SWEEP_ANGLE = 150;
    private final int DEFAULT_MAX_PROGRESS = 100;

    private final int DEFAULT_ARC_COLOR = Color.RED;
    private final int DEFAULT_BG_COLOR = Color.DKGRAY;
    private final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private final int DEFAULT_TEXT_SIZE = 40;

    private int mArcColor = DEFAULT_ARC_COLOR;
    private int mBgColor = DEFAULT_BG_COLOR;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextSize = DEFAULT_TEXT_SIZE;

    private int progress = 50;
    private int mMaxProgress = DEFAULT_MAX_PROGRESS;

    private Paint mCirclePaint;
    private Paint mBgPaint;
    private Paint mTextPaint;

    private final Rect mTextBound = new Rect();

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
                R.styleable.ArcView);

        mArcColor = attributes
                .getColor(
                        R.styleable.ArcView_arcColor,
                        DEFAULT_ARC_COLOR);

        mBgColor = attributes
                .getColor(
                        R.styleable.ArcView_bgColor,
                        DEFAULT_BG_COLOR);

        mTextColor = attributes
                .getColor(
                        R.styleable.ArcView_arc_textColor,
                        DEFAULT_TEXT_COLOR);

        mTextSize = (int) attributes
                .getDimension(
                        R.styleable.ArcView_arc_textSize,
                        DEFAULT_TEXT_SIZE);
        attributes.recycle();
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mArcColor);
        mCirclePaint.setStrokeWidth(8.0F);
        mCirclePaint.setDither(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);


        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(20.0F);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(4);

        //字体SP单位转换成PX
        int size =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                mTextSize, getResources().getDisplayMetrics());
        mTextPaint.setTextSize(size);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int circleWidth = getWidth() - paddingLeft - paddingRight;
        int circleHeight = getHeight() - paddingTop - paddingBottom;

        int radius = Math.min(circleWidth, circleHeight) / 2;

        int left = getLeft() + paddingLeft;
        int right = left + radius * 2;
        int top = getTop() + getPaddingTop();
        int bottom = top + 2 * radius;
        canvas.drawArc(left, top, right, bottom, START_SWEEP_ANGLE, MAX_SWEEP_ANGLE, false,
                mBgPaint);

        int sweepArc = MAX_SWEEP_ANGLE * progress / mMaxProgress;
        canvas.drawArc(left, top, right, bottom, START_SWEEP_ANGLE, sweepArc, false, mCirclePaint);


        mTextPaint.getTextBounds(String.valueOf(progress), 0, String.valueOf(progress).length(),
                mTextBound);
        canvas.drawText(String.valueOf(progress), (left + right) / 2 - mTextBound.width() / 2,
                (top + bottom) / 2 + mTextBound.height() / 2, mTextPaint);
    }

    /**
     * 设置进度大小
     */
    public void setProgress(int progress) {
        if (progress < 0 || progress > mMaxProgress) {
            return;
        }
        this.progress = progress;
        invalidate();
    }

    /**
     * 设置最大进度值
     */
    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }
}
