package com.nexuslink.cyclenavi.Model.Impl;

import android.graphics.drawable.Drawable;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.Interface.IMainModel;
import com.nexuslink.cyclenavi.Tools.RetrofitWrapper;

/**
 * Created by Rye on 2017/3/28.
 */

public class MainModel implements IMainModel {
    /**
     * 第二次登录时会获得缓存的头像
     */
    @Override
    public Drawable getPersonalImage() {

        return null;
    }
}
