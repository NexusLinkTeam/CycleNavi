package com.nexuslink.cyclenavi.Util;

import com.nexuslink.cyclenavi.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rye on 2017/5/8.
 */

public class LoginStatus {
    public static Boolean check(){
        return  MyApplication.getContext().getSharedPreferences("CycleNaviData",MODE_PRIVATE).
                getBoolean("isUserLogin",false);
    }
}
