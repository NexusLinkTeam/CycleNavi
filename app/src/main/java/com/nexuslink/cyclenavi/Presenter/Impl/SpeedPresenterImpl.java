package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.nexuslink.cyclenavi.Model.Impl.SpeedModel;
import com.nexuslink.cyclenavi.Model.Interface.ISpeedModel;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.Util.MediaScanner;
import com.nexuslink.cyclenavi.View.Interface.ISpeedView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.R.attr.path;

/**
 * Created by Rye on 2017/3/28.
 */

public class SpeedPresenterImpl implements ISpeedPresenter {
    private ISpeedView speedView;
    private ISpeedModel speedModel;
    public SpeedPresenterImpl(ISpeedView speedView) {
        this.speedView = speedView;
        speedView.setPresenter(this);
        speedModel = new SpeedModel();
    }

    @Override
    public void start() {
        int persent = 0;
        speedModel.currentSpeed();
        speedView.showCurrentSpeed(persent);
    }

    @Override
    public void startCycle() {
        speedView.showStart();
    }

    @Override
    public void pauseCycle() {
        speedView.showPause();
    }

    @Override
    public void switchFragment() {
        speedView.showPage2();
    }

   /* @Override
    public void scan(Context context) {
        speedModel.addToGallery(context);
    }*/


}
