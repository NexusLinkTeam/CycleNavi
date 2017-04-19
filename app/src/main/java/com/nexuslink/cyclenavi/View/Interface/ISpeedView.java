package com.nexuslink.cyclenavi.View.Interface;

import com.nexuslink.cyclenavi.BasePresenter;
import com.nexuslink.cyclenavi.BaseView;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;

/**
 * Created by Rye on 2017/3/28.
 */

public interface ISpeedView extends BaseView<ISpeedPresenter> {

    void showCurrentSpeed(int persent);

    void showPause();

    void showStart();

    void showPage2();

}
