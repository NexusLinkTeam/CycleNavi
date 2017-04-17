package com.nexuslink.cyclenavi.Util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rye on 2017/4/17.
 */

public class SpUtil {

    public static String getUserName(Context context){
        return context.getSharedPreferences("CycleNaviData", MODE_PRIVATE).getString("name","name");
    }
    public static String getUserId(Context context){
        //未登录会报错
        return context.getSharedPreferences("CycleNaviData", MODE_PRIVATE).getString("uid","");
    }
    public static Boolean getLoginStatus(Context context){
        //未登录会报错
        return context.getSharedPreferences("CycleNaviData", MODE_PRIVATE).getBoolean("isUserLogin",false);
    }


    public static void setLoginStatus(Context context,boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CycleNaviData",MODE_PRIVATE);

        sharedPreferences.edit().
                putBoolean("isUserLogin",b).apply();
    }

    public static void setUserName(Context context,String userName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CycleNaviData",MODE_PRIVATE);
        sharedPreferences.edit().
                putString("name",userName).apply();
    }

    public static void setUserId(Context context,String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CycleNaviData",MODE_PRIVATE);
        sharedPreferences.edit().
        putString("uid",s).apply();
    }
}
