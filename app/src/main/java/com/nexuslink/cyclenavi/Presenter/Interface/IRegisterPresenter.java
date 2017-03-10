package com.nexuslink.cyclenavi.Presenter.Interface;

import android.widget.EditText;

/**
 * Created by Rye on 2017/1/22.
 */

public interface IRegisterPresenter {
    void registerNewUser(EditText userName, EditText userPassword,EditText rePassword, EditText emergencyPhone);

    void registerOk();

    void registerWrong();

    void infoError(EditText userName, String reason);

    void netWorkWrong();

    void phoneNumWrong();
}
