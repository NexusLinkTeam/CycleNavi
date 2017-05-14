package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;

import com.nexuslink.cyclenavi.BasePresenter;

/**
 * Created by Rye on 2017/1/18.
 */

public interface IMainPresenter extends BasePresenter{
    void exit();

    void moreUserInfo(Context context);

    void finishLogin();

    //已经登录，请求获得用户头像，Glide自带缓存是否冲突
    void requestUserPic();


}
