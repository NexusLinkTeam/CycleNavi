package com.nexuslink.cyclenavi.Presenter.Impl;

import android.support.v4.app.Fragment;

import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;
import com.nexuslink.cyclenavi.View.Interface.IMainView;


/**
 * Created by Rye on 2017/1/18.
 */

public class MainPresenterImpl implements IMainPresenter {
    private IMainView iMainView;

    public MainPresenterImpl(IMainView mainActivity) {
        this.iMainView = mainActivity;
    }

    @Override
    public void switchFragment(Fragment fragment) {
        iMainView.ReplaceFragment(fragment);
    }

    @Override
    public void showDialog() {
        iMainView.SetExistDialog();
    }
}
