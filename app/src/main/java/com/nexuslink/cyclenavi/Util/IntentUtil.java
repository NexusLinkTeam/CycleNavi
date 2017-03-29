package com.nexuslink.cyclenavi.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 简单地封装下Intent
 * Created by Rye on 2017/3/28.
 */

public class IntentUtil {
    public static void startActivity(Context context, Class<?> target){
        Intent intent = new Intent(context,target);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, Class<?> target, int requestCode){
        Intent intent = new Intent(context,target);
        context.startActivityForResult(intent,requestCode);
    }
}
