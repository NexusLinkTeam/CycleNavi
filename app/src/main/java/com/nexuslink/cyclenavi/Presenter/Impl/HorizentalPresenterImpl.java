package com.nexuslink.cyclenavi.Presenter.Impl;

import android.hardware.SensorEvent;
import android.icu.text.DecimalFormat;

import com.nexuslink.cyclenavi.Presenter.Interface.IHorizentalPresenter;
import com.nexuslink.cyclenavi.View.Interface.IHorizentalView;

/**
 * Created by Rye on 2017/3/29.
 */

public class HorizentalPresenterImpl implements IHorizentalPresenter {

    private IHorizentalView horizentalView;

    public HorizentalPresenterImpl(IHorizentalView horizentalFragment) {
        this.horizentalView = horizentalFragment;
        this.horizentalView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void caculateDegree(SensorEvent sensorEvent) {
        //===================================计算坡度的算法==================================
        float g = (float) Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0]
                + sensorEvent.values[1] * sensorEvent.values[1]
                + sensorEvent.values[2] * sensorEvent.values[2]);
        float gradient = (float) Math.acos(Math.abs(sensorEvent.values[2]) / g);
        horizentalView.showDegree(gradient);
    }

    @Override
    public void caculatePress(SensorEvent sensorEvent) {
        float sPV = sensorEvent.values[0];
        DecimalFormat df = new DecimalFormat("0.00");
        df.getRoundingMode();
        // ====================================计算海拔=====================================
        double height = 44330000*(1-(Math.pow((Double.parseDouble(df.format(sPV))/1013.25),
                (float)1.0/5255.0)));
        horizentalView.setHight(df.format(height));
        horizentalView.setPress(sPV);
    }
}
