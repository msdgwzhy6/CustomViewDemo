package cn.onlyloveyd.customview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.onlyloveyd.customview.fragment.DotIndicatorFragment;

/**
 * 文 件 名: CustomViewPagerAdapter
 * 创 建 人: 易冬
 * 创建日期: 2017/10/13 08:17
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 */
public class CustomViewPagerAdapter extends FragmentPagerAdapter {
    final List<String> mTitles;

    public CustomViewPagerAdapter(FragmentManager fm, List titles) {
        super(fm);
        this.mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return DotIndicatorFragment.newInstance(mTitles.get(position));
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
