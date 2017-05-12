package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;
import com.nexuslink.cyclenavi.Model.Impl.InfoModel;
import com.nexuslink.cyclenavi.Model.Interface.IInfoModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IInfoPresenter;
import com.nexuslink.cyclenavi.View.Interface.IInfoView;

import java.io.File;

/**
 * Created by Rye on 2017/4/19.
 */

public class InfoPresenterImpl implements IInfoPresenter {
    private IInfoView infoView;
    private IInfoModel infoModel;
    public InfoPresenterImpl(IInfoView infoView) {
        this.infoView = infoView;
        infoModel = new InfoModel(this);
    }

    //拍照
    @Override
    public void takePhoto(Context context) {
        infoModel.storePic(context);
        infoView.showCamera();
    }

    //？？？
    @Override
    public Uri getUri() {
        return infoModel.getUri();

    }

    //扫描图片到图库
    @Override
    public void scan(Context context) {
        infoModel.addToGallery(context);
    }

    @Override
    public void getShortCut(final Bitmap bitmap, final ViewGroup viewContainer, final MapView mapView, final View...views) {
        infoModel.getScreenShot(bitmap, viewContainer, mapView, views);
    }

    @Override
    public void requestShortCutSuccess(File file) {
        infoView.showShortCut(file);
    }
}
