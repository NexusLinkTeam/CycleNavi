package com.nexuslink.cyclenavi.Model.Impl;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.Interface.IRegisterModel;
import com.nexuslink.cyclenavi.Model.JavaBean.RegisterBean;
import com.nexuslink.cyclenavi.Presenter.Impl.RegisterPresenterImpl;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.View.Interface.IRegisterView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rye on 2017/1/22.
 */

public class RegisterModel implements IRegisterModel {
    private RegisterPresenterImpl registerPresenterImpl;

    //注册新用户
    @Override
    public void registerNew(String username, String userPassword, String PhoneNum, IRegisterView iRegisterView) {
        registerPresenterImpl = new RegisterPresenterImpl(iRegisterView);

        RetrofitWrapper.getInstance().create(ICycleNaviService.class).register(username, userPassword, PhoneNum)
                .enqueue(new Callback<RegisterBean>() {
                    @Override
                    public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                        if(response.body().getCode() == 200){
                            registerPresenterImpl.registerOk();

                        }else {
                            registerPresenterImpl.registerWrong();
                            registerPresenterImpl.phoneNumWrong();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterBean> call, Throwable t) {
                        registerPresenterImpl.registerWrong();
                        registerPresenterImpl.netWorkWrong();
                    }
                });
    }

    //检查用户数据
    @Override
    public boolean checkoutUserInfo(EditText userName, EditText userPassword, EditText rePassword, EditText emergencyPhone
    , IRegisterView iRegisterView) {
        String password = userPassword.getText().toString();
        String repass = rePassword.getText().toString();
        String name = userName.getText().toString();
        String phone = emergencyPhone.getText().toString();
        View focus = null;
        boolean cancel = false;
        registerPresenterImpl = new RegisterPresenterImpl(iRegisterView);
        if (TextUtils.isEmpty(name)) {
            registerPresenterImpl.infoError(userName,"用户名为空");
            focus = userName;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            registerPresenterImpl.infoError(userPassword,"密码需大于6位");
            focus = userPassword;
            cancel = true;
        } else if (!isPasswordEqual(password, repass)) {
            registerPresenterImpl.infoError(rePassword,"密码不一致");
            focus = rePassword;
            cancel = true;
        } else if (!isPhoneCountRight(phone)) {
            registerPresenterImpl.infoError(emergencyPhone,"号码位数为11位");
            focus = emergencyPhone;
            cancel = true;
        }
        if (cancel) {
            focus.requestFocus();
        }
        return !cancel;
    }
    private boolean isPhoneCountRight(String phoneNum) {
        return phoneNum.length() == 11;
    }

    /**
     * 检查密码是否相等
     * @param s
     * @param s1
     * @return 相等返回true，不相等返回false
     */
    private boolean isPasswordEqual(String s, String s1) {
        if (s.equals(s1)){
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        return  password.length() >= 6;
    }
}
