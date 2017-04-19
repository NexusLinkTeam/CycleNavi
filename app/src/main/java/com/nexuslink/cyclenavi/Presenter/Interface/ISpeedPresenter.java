package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;
import android.net.Uri;

import com.nexuslink.cyclenavi.BasePresenter;

/**
 * Created by Rye on 2017/3/28.
 */

public interface ISpeedPresenter extends BasePresenter{
    void startCycle();

    void pauseCycle();

    void switchFragment();


/*
    void scan(Context context);
*/
}
