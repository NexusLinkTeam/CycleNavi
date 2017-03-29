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

public class DataActivity extends AppCompatActivity implements SensorEventListener{

    @BindView(R.id.viewpager_data)
    ViewPager dataPager;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();
        initData();
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HorizentalFragment());
        dataPager.setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager(),
                fragments));
    }

    private void initData() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
