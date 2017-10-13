package cn.onlyloveyd.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.adapter.CustomAdapter;

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    private CustomAdapter mCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        Slide slide = new Slide();
        slide.setDuration(200);
        getWindow().setExitTransition(slide);

        mCustomAdapter = new CustomAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRvContent.setAdapter(mCustomAdapter);
        mRvContent.setLayoutManager(linearLayoutManager);
        mRvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}
