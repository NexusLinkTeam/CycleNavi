package com.nexuslink.cyclenavi.View.Interface;

import com.nexuslink.cyclenavi.BaseView;
import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;

/**
 * Created by Rye on 2017/1/18.
 */

public interface IMainView extends BaseView<IMainPresenter>{
    void showExitDialog();

    void showPersonZoom();

    void showPrepareToLogin();

    void showStatus();

    void showUserPhoto(String personalImage);


    void loadView(String image);
}
