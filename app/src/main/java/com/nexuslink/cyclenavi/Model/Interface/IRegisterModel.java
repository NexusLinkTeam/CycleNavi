package com.nexuslink.cyclenavi.Model.Interface;

import android.widget.EditText;

import com.nexuslink.cyclenavi.View.Interface.IMainView;
import com.nexuslink.cyclenavi.View.Interface.IRegisterView;

/**
 * Created by Rye on 2017/1/22.
 */

public interface IRegisterModel {
    void registerNew(String username, String userPassword, String PhoneNum, IRegisterView iRegisterView);

    boolean checkoutUserInfo(EditText userName, EditText userPassword, EditText rePassword, EditText emergencyPhone, IRegisterView iRegisterView);
}
