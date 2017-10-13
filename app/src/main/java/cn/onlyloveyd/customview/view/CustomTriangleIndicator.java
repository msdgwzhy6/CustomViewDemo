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
    private Path mTriangePath;

    private ViewPager mViewPager;
    private final LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(0,
            LayoutParams.MATCH_PARENT, 1.0f);


    private float mTabWidth;
    private float mTabOffset;

    //矩形指示器的起点X坐标
    private float mTriangleStartPosition;

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
        mTriangleStartPosition = (mTabWidth - mTriangleWidth) /2;
    }

    private void init() {
        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setColor(mTriangleColor);
        mTrianglePaint.setStrokeCap(Paint.Cap.ROUND);
        mTrianglePaint.setStyle(Paint.Style.FILL);

        mTriangePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTriangePath.reset();
        float currentStartPosition = mTriangleStartPosition + mTabOffset;
        mTriangePath.moveTo(currentStartPosition , getHeight());
        mTriangePath.lineTo(currentStartPosition + mTriangleWidth/2, getHeight()-mTriangleHeight);
        mTriangePath.lineTo(currentStartPosition + mTriangleWidth, getHeight());
        mTriangePath.close();
        canvas.drawPath(mTriangePath, mTrianglePaint);
    }
}
