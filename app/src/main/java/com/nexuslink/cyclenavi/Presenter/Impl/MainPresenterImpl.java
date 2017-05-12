package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;

import com.nexuslink.cyclenavi.Model.Impl.MainModel;
import com.nexuslink.cyclenavi.Model.Interface.IMainModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;
import com.nexuslink.cyclenavi.Util.LoginStatus;
import com.nexuslink.cyclenavi.View.Interface.IMainView;


/**
 * Created by Rye on 2017/1/18.
 */

public class MainPresenterImpl implements IMainPresenter {
    private IMainView iMainView;
    private IMainModel iMainModel;

    public MainPresenterImpl(IMainView mainActivity) {
        this.iMainView = mainActivity;
        //构造方法中将presenter实例传给view
        iMainView.setPresenter(this);
        iMainModel = new MainModel();
    }

    @Override
    public void start() {
        // TODO: 2017/5/8 缓存下来
        //获取侧滑栏的图片
        //这里要请求图片=====================================待完成===================================
      /*  iMainView.showUserPhoto(iMainModel.getPersonalImage());*/
    }

    //
    @Override
    public void exit() {
        iMainView.showExitDialog();
    }

    // 判断是否登录，完成相应的跳转
    @Override
    public void moreUserInfo(Context context) {
        // TODO: 2017/5/8 工具类完成检验（待测试）
        if(LoginStatus.check()){
            iMainView.showPersonZoom();
        }else {
            iMainView.showPrepareToLogin();
        }
    }

    //完成登陆时
    @Override
    public void finishLogin() {
        iMainView.showStatus();
    }

    @Override
    public void requestUserPic() {

    }

}
