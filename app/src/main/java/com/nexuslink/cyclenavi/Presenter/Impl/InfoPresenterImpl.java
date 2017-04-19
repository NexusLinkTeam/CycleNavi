package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;
import android.net.Uri;

import com.nexuslink.cyclenavi.Model.Impl.InfoModel;
import com.nexuslink.cyclenavi.Model.Interface.IInfoModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IInfoPresenter;
import com.nexuslink.cyclenavi.View.Impl.Fragments.InfoFragment;
import com.nexuslink.cyclenavi.View.Interface.IInfoView;

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

    @Override
    public void takePhoto(Context context) {
        infoModel.storePic(context);
        infoView.showCamera();
    }

    @Override
    public Uri getUri() {
        return infoModel.getUri();

    }

    @Override
    public void scan(Context context) {
        infoModel.addToGallery(context);
    }
}
