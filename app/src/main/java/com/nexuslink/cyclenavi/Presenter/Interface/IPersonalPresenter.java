package com.nexuslink.cyclenavi.Presenter.Interface;

import android.net.Uri;

import com.nexuslink.cyclenavi.View.Impl.Activities.PersonalActivity;

/**
 * Created by Rye on 2017/1/25.
 */

public interface IPersonalPresenter {
    void logout();

    void upLoadPhoto(Uri uri, PersonalActivity personalActivity);
}
