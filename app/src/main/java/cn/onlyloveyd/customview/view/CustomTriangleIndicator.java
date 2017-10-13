package cn.onlyloveyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.onlyloveyd.customview.R;

/**
 * 文 件 名: CustomTriangleIndicator
 * 创 建 人: 易冬
 * 创建日期: 2017/10/13 14:21
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 */
public class CustomTriangleIndicator extends LinearLayout {

    //自定义属性默认值
    private final int DEFAULT_TRIANGLE_COLOR = Color.GREEN;
    private final int DEFAULT_TRIANGLE_WIDTH = 10;
    private final int DEFAULT_TRIANGLE_HEIGHT = 6;

    //自定义属性
    private int mTriangleColor = DEFAULT_TRIANGLE_COLOR;
    private int mTriangleWidth = DEFAULT_TRIANGLE_WIDTH;
    private int mTriangleHeight = DEFAULT_TRIANGLE_HEIGHT;

    //指示器画笔
    private Paint mTrianglePaint;

    private ViewPager mViewPager;
    private int mTabCount = -1;
    private final LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(0,
            LayoutParams.MATCH_PARENT, 1.0f);


    private float mTabWidth;
    private float mTabOffset;

    //当前ViewPage index
    private int currentIndex = 0;
    //矩形指示器的起点X坐标
    private float mTriangleStartPosition;

    public ViewPager getViewPager() {
        return mViewPager;
    }

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
                currentIndex = position;
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
        mTabCount = mViewPager.getAdapter().getCount();
        for (int i = 0; i < mTabCount; i++) {
            addTextTab(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }
    }

    public CustomTriangleIndicator(Context context) {
        super(context);
    }

    public CustomTriangleIndicator(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTriangleIndicator(Context context,
            @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
                R.styleable.CustomTriangleIndicator);

        mTriangleColor = attributes.getColor(R.styleable.CustomTriangleIndicator_triangleColor, DEFAULT_TRIANGLE_COLOR);
        mTriangleWidth = (int) attributes.getDimension(R.styleable.CustomTriangleIndicator_triangleWidth, DEFAULT_TRIANGLE_WIDTH);
        mTriangleHeight = (int) attributes.getDimension(R.styleable.CustomTriangleIndicator_triangleHeight, DEFAULT_TRIANGLE_HEIGHT);

        attributes.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w * 1.0f / getChildCount();
        mTriangleStartPosition = currentIndex * mTabWidth + (mTabWidth - mTriangleWidth) /2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setColor(mTriangleColor);
        mTrianglePaint.setStrokeCap(Paint.Cap.ROUND);
        mTrianglePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        //实例化路径
        Path path = new Path();
        float currentStartPosition = mTriangleStartPosition + mTabOffset;
        path.moveTo(currentStartPosition , getHeight());// 此点为多边形的起点
        path.lineTo(currentStartPosition + mTriangleWidth/2, getHeight()-mTriangleHeight);
        path.lineTo(currentStartPosition + mTriangleWidth, getHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }
}
