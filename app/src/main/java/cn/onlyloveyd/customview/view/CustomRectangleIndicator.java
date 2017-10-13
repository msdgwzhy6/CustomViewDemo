package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.onlyloveyd.customview.R;

/**
 * 文 件 名: CustomRectangleIndicator
 * 创 建 人: 易冬
 * 创建日期: 2017/10/13 09:43
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 */
public class CustomRectangleIndicator extends LinearLayout{
    //自定义属性默认值
    private final int DEFAULT_RECT_COLOR = Color.GREEN;
    private final int DEFAULT_RECT_HEIGHT = 4;

    //自定义属性
    private int mRectColor = DEFAULT_RECT_COLOR;
    private int mRectHeight = DEFAULT_RECT_HEIGHT;


    //指示器画笔
    private Paint mRectPaint;

    private ViewPager mViewPager;
    private final LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(0,
            LayoutParams.MATCH_PARENT, 1.0f);


    private float mTabWidth;
    private float mTabOffset;

    //矩形指示器的起点X坐标
    private float mRectStartPosition;

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (viewPager.getAdapter() == null || viewPager.getAdapter().getCount() == 0) {
            throw new IllegalArgumentException();
        }
        notifyViewPagerChanged();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                mTabOffset = mTabWidth * position + mTabWidth * positionOffset;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTextTab(final int index, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        this.addView(tab, index, defaultLayoutParams);
    }


    private void notifyViewPagerChanged() {
        this.removeAllViews();
        int tabCount = mViewPager.getAdapter().getCount();
        for (int i = 0; i < tabCount; i++) {
            addTextTab(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }
    }

    public CustomRectangleIndicator(Context context) {
        super(context);
    }

    public CustomRectangleIndicator(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRectangleIndicator(Context context,
            @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
                R.styleable.CustomRectangleIndicator);

        mRectColor = attributes.getColor(R.styleable.CustomRectangleIndicator_rectColor, DEFAULT_RECT_COLOR);
        mRectHeight = (int) attributes.getDimension(R.styleable.CustomRectangleIndicator_rectHeight,
                DEFAULT_RECT_HEIGHT);
        attributes.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w * 1.0f / getChildCount();
        mRectStartPosition = 0;
    }

    private void init() {
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(mRectColor);
        mRectPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRectStartPosition + mTabOffset, getHeight() - mRectHeight , mRectStartPosition + mTabOffset + mTabWidth , getHeight(), mRectPaint);
    }
}
