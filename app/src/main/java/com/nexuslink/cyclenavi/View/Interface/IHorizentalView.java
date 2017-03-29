package com.nexuslink.cyclenavi.View.Interface;

import com.nexuslink.cyclenavi.BaseView;
import com.nexuslink.cyclenavi.Presenter.Interface.IHorizentalPresenter;

/**
 * Created by Rye on 2017/3/29.
 */

public interface IHorizentalView extends BaseView<IHorizentalPresenter> {
    void showDegree(float gradient);

    void setHight(String height);

    void setPress(float sPV);
}
