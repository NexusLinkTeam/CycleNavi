package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.nexuslink.cyclenavi.BasePresenter;
import com.nexuslink.cyclenavi.View.Impl.Activities.MainActivity;

/**
 * Created by Rye on 2017/1/18.
 */

public interface IMainPresenter extends BasePresenter{
    void exit();

    void moreUserInfo(Context context);

    void finishLogin();

}
