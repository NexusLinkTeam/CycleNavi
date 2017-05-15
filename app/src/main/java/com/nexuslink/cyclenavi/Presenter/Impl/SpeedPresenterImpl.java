package com.nexuslink.cyclenavi.Presenter.Impl;

import com.nexuslink.cyclenavi.Model.Impl.SpeedModel;
import com.nexuslink.cyclenavi.Model.Interface.ISpeedModel;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.View.Interface.ISpeedView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rye on 2017/3/28.
 */

public class SpeedPresenterImpl implements ISpeedPresenter {
    private ISpeedView speedView;
    private ISpeedModel speedModel;
    public SpeedPresenterImpl(ISpeedView speedView) {
        this.speedView = speedView;
        speedView.setPresenter(this);
        speedModel = new SpeedModel(this);
    }
    //开始骑行时
    @Override
    public void startCycle() {
        speedView.showStart();
    }

    //完成骑行时
    @Override
    public void pauseCycle(RequestBody userId,
                           RequestBody totalTime,
                           RequestBody date,
                           RequestBody routeLine,
                           RequestBody speedList,
                           RequestBody heightList,
                           MultipartBody.Part picture) {
        speedModel.uploadCurrent(userId, totalTime, date, routeLine, speedList, heightList, picture);
        speedView.showFinish();
    }

    @Override
    public void responseSuccess() {
        speedView.hideProgress();
    }
}
