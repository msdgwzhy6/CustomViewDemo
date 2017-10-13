package cn.onlyloveyd.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 文 件 名: DotIndicatorFragment
 * 创 建 人: 易冬
 * 创建日期: 2017/10/13 08:13
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：
 */
public class DotIndicatorFragment extends Fragment {

    public static DotIndicatorFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        DotIndicatorFragment fragment = new DotIndicatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle!=null?bundle.getString("TITLE"):null;
        TextView  textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(40);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText(title);
        return textView;
    }
}
