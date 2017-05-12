package com.nexuslink.cyclenavi.Util;

import android.widget.Toast;

import com.nexuslink.cyclenavi.MyApplication;

/**
 * Created by Rye on 2017/5/5.
 */

public class ToastUtil {
    public static void shortToast(String msg){
        Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void longToast(String msg){
        Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void shortToast(int strId){
        Toast.makeText(MyApplication.getContext(),strId,Toast.LENGTH_SHORT).show();
    }
    public static void longToast(int strId){
        Toast.makeText(MyApplication.getContext(),strId,Toast.LENGTH_SHORT).show();
    }
}
