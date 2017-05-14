package com.nexuslink.cyclenavi.Presenter.Interface;

import android.hardware.SensorEvent;

/**
 * Created by Rye on 2017/3/29.
 */

public interface IHorizentalPresenter{
    void caculateDegree(SensorEvent sensorEvent);

    void caculatePress(SensorEvent sensorEvent);
}
