package com.nexuslink.cyclenavi.Presenter.Impl;

import android.widget.EditText;

import com.nexuslink.cyclenavi.Model.Impl.RegisterModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IRegisterPresenter;
import com.nexuslink.cyclenavi.View.Interface.IRegisterView;

/**
 * Created by Rye on 2017/1/22.
 */

public class RegisterPresenterImpl implements IRegisterPresenter {
    private IRegisterView iRegisterView;
    private RegisterModel registerModel;

    public RegisterPresenterImpl(IRegisterView registeractivity) {
        this.iRegisterView = registeractivity;
        registerModel = new RegisterModel();
    }

    //注册
    @Override
    public void registerNewUser(EditText userName, EditText userPassword, EditText rePassword, EditText emergencyPhone) {
        if(registerModel.checkoutUserInfo(userName, userPassword, rePassword, emergencyPhone,iRegisterView)){
            String password = userPassword.getText().toString();
            String name = userName.getText().toString();
            String phone = emergencyPhone.getText().toString();
            registerModel.registerNew(name, password, phone, iRegisterView);
            iRegisterView.onRegisterProgress();
        }
        }

    //注册成功时
    @Override
    public void registerOk() {
        iRegisterView.onCancelRegisterProgress();
        iRegisterView.onFinishRegister();
    }

    //注册发生错误
    @Override
    public void registerWrong() {
        iRegisterView.onCancelRegisterProgress();
    }

    //信息错误
    @Override
    public void infoError(EditText editText, String reason) {
        iRegisterView.showErrorReason(editText,reason);
    }

    //网络错误
    @Override
    public void netWorkWrong() {
        iRegisterView.showNetWorkWrong();
    }

    //电话号码错误
    @Override
    public void phoneNumWrong() {
        iRegisterView.showPhoneNumWrong();
    }


}
