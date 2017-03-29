package com.nexuslink.cyclenavi.View.Impl.Fragments;


import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Presenter.Impl.HorizentalPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.IHorizentalPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.RadUtil;
import com.nexuslink.cyclenavi.View.Interface.IHorizentalView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizentalFragment extends Fragment implements IHorizentalView,SensorEventListener {

    private SensorManager sensorManager;


    private IHorizentalPresenter presenter;

    @BindView(R.id.text_degree)
    TextView textDegree;

    @BindView(R.id.text_press)
    TextView textPress;

    @BindView(R.id.text_height)
    TextView textHeight;


    public HorizentalFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horizental, container, false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData() {
       new HorizentalPresenterImpl(this);
        sensorManager = (SensorManager)getContext().getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void setPresenter(IHorizentalPresenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showDegree(float gradient) {
        textDegree.setText(Math.round(RadUtil.grad2Deg(gradient)) + "º");
    }

    @Override
    public void setHight(String height) {
        textPress.setText("当前海拔\n"+ height + "米");
    }

    @Override
    public void setPress(float sPV) {
        textHeight.setText("当前大气压强\n"+sPV+"帕");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                presenter.caculateDegree(sensorEvent);
                break;
            case Sensor.TYPE_PRESSURE:
                presenter.caculatePress(sensorEvent);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
