package com.nexuslink.cyclenavi.View.Interface;

import android.widget.EditText;

/**
 * Created by Rye on 2017/1/22.
 */

public interface IRegisterView {
    void onRegisterProgress();

    void onCancelRegisterProgress();

    void showNetWorkWrong();

    void showErrorReason(EditText editText, String reason);

    void onFinishRegister();

    void showPhoneNumWrong();
}
