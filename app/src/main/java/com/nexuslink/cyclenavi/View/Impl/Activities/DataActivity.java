package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nexuslink.cyclenavi.Adapters.MainFragmentStatePagerAdapter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Fragments.HorizentalFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.InfoFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.SpeedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataActivity extends AppCompatActivity{

    @BindView(R.id.viewpager_data)
    ViewPager dataPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HorizentalFragment());
        dataPager.setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager(),
                fragments));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
