package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;

import com.nexuslink.cyclenavi.Model.Impl.MainModel;
import com.nexuslink.cyclenavi.Model.Interface.IMainModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;
import com.nexuslink.cyclenavi.View.Impl.Activities.MainActivity;
import com.nexuslink.cyclenavi.View.Interface.IMainView;

import static android.content.Context.MODE_PRIVATE;


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
        //这里要请求图片=====================================待完成===================================
      /*  iMainView.showUserPhoto(iMainModel.getPersonalImage());*/
    }

    @Override
    public void exit() {
        iMainView.showExitDialog();
    }

    @Override
    public void moreUserInfo(Context context) {
        Boolean isLogin = context.getSharedPreferences("CycleNaviData",MODE_PRIVATE).
                getBoolean("isUserLogin",false);

        if(isLogin){
            iMainView.showPersonZoom();
        }else {
            iMainView.showPrepareToLogin();
        }
    }

    @Override
    public void finishLogin() {
        iMainView.showStatus();
    }

}
