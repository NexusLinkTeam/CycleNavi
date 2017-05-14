package com.nexuslink.cyclenavi.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nexuslink.cyclenavi.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rye on 2017/4/17.
 */

public class SpUtil {

    private static final String SP_NAME = "CycleNaviData";
    private static final String NAME = "name";
    private static final String NAME_DEFAULT = "未登录";
    private static final String IS_LOGIN = "isUserLogin";
    private static final String UID = "uid";
    private static final int UID_DEFAULT = -1;
    private static final String USER_IMAGE_DEFAULT = "http://120.77.87.78:8080/cycle/image/default.jpg";

    public static String getUserName(){
        return MyApplication
                .getContext()
                .getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getString(NAME,NAME_DEFAULT);
    }

    //UID被设置为int,之前版本会报错
    public static int getUserId(){
        return MyApplication
                .getContext()
                .getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getInt(UID,UID_DEFAULT);

    }
    public static Boolean getLoginStatus(){
        return MyApplication
                .getContext()
                .getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getBoolean(IS_LOGIN,false);
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

    public static void setUserId(Context context,int s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CycleNaviData",MODE_PRIVATE);
        sharedPreferences.edit().
        putInt("uid",s).apply();
    }


    public static String getUserImage() {
        return MyApplication
                .getContext()
                .getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getString("image",USER_IMAGE_DEFAULT);
    }

    public static void setUserImage(String s) {
        SharedPreferences sharedPreferences = MyApplication
                .getContext().getSharedPreferences("CycleNaviData",MODE_PRIVATE);
        sharedPreferences.edit().
                putString("image",s).apply();
    }
}
