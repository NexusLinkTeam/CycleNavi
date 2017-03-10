package com.nexuslink.cyclenavi.Presenter.Impl;

import android.net.Uri;

import com.nexuslink.cyclenavi.Model.Impl.PersonalModel;
import com.nexuslink.cyclenavi.Model.Interface.IPersonalModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IPersonalPresenter;
import com.nexuslink.cyclenavi.View.Impl.Activities.PersonalActivity;
import com.nexuslink.cyclenavi.View.Interface.IPersonalView;

/**
 * Created by Rye on 2017/1/25.
 */

public class PersonalPresenterImpl implements IPersonalPresenter {
    private IPersonalModel iLogoutModel;
    private IPersonalView personalView;

    public PersonalPresenterImpl(IPersonalView personalActivity) {
        iLogoutModel = new PersonalModel();
        this.personalView = personalActivity;
    }

    @Override
    public void logout() {
        iLogoutModel.cancelLoginStatus(personalView.getCurrentActivity());
    }

    @Override
    public void upLoadPhoto(Uri uri, PersonalActivity personalActivity) {
        iLogoutModel.upLoad(uri,personalActivity);
    }
}
