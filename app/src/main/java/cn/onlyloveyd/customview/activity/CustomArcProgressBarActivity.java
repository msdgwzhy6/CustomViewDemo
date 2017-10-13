package cn.onlyloveyd.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onlyloveyd.customview.R;
import cn.onlyloveyd.customview.view.ArcView;

public class CustomArcProgressBarActivity extends AppCompatActivity {
    @BindView(R.id.sb_indicator)
    SeekBar mSbIndicator;
    @BindView(R.id.av_progress)
    ArcView mArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_progressbar);
        ButterKnife.bind(this);

        Explode explode = new Explode();
        explode.setDuration(200);
        getWindow().setEnterTransition(explode);

        mSbIndicator.setMax(100);
        mArcView.setMaxProgress(mSbIndicator.getMax());
        mSbIndicator.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mArcView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
