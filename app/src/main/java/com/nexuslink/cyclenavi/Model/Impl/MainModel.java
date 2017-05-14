package com.nexuslink.cyclenavi.Model.Impl;

import com.nexuslink.cyclenavi.BasePresenter;
import com.nexuslink.cyclenavi.Extra.Net.ApiService;
import com.nexuslink.cyclenavi.Extra.Net.RetrofitClient;
import com.nexuslink.cyclenavi.Model.Interface.IMainModel;
import com.nexuslink.cyclenavi.Model.JavaBean.GetUserInfoBean;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.Util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rye on 2017/3/28.
 */

public class MainModel implements IMainModel {
    //获得用户信息
    @Override
    public void getPersonalImage(final BasePresenter call) {
        int userId = SpUtil.getUserId();
        RetrofitClient.create(ApiService.class)
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetUserInfoBean>() {
                    @Override
                    public void accept(@NonNull GetUserInfoBean getUserInfo) throws Exception {
                        call.responseSuccess(getUserInfo);
                        ToastUtil.shortToast(getUserInfo.getUser().getUserImg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.shortToast(throwable.getMessage());
                    }
                });
    }
}
