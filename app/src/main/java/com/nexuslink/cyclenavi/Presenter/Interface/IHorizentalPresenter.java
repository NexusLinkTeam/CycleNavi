package com.nexuslink.cyclenavi.Presenter.Interface;

import android.hardware.SensorEvent;

import com.nexuslink.cyclenavi.BasePresenter;

/**
 * Created by Rye on 2017/3/29.
 */

public interface IHorizentalPresenter extends BasePresenter {
    void caculateDegree(SensorEvent sensorEvent);

    void caculatePress(SensorEvent sensorEvent);
}
