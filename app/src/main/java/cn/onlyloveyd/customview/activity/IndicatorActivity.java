package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.adapter.CustomViewPagerAdapter;
import cn.onlyloveyd.customview.view.CustomDotIndicator;
import cn.onlyloveyd.customview.view.CustomRectangleIndicator;
import cn.onlyloveyd.customview.view.CustomTriangleIndicator;

public class IndicatorActivity extends AppCompatActivity {

    @BindView(R.id.customDotIndicator)
    CustomDotIndicator mCustomDotIndicator;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.customRectIndicator)
    CustomRectangleIndicator mCustomRectIndicator;
    @BindView(R.id.customTriangleIndicator)
    CustomTriangleIndicator mCustomTriangleIndicator;

    private List<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_indicator);
        ButterKnife.bind(this);

        mTitles.add("体育");
        mTitles.add("军事");
        mTitles.add("娱乐");
        mTitles.add("人文");

        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(
                getSupportFragmentManager(), mTitles);
        mVp.setAdapter(customViewPagerAdapter);
        mCustomDotIndicator.setViewPager(mVp);
        mCustomRectIndicator.setViewPager(mVp);
        mCustomTriangleIndicator.setViewPager(mVp);
    }
}
