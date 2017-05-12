package com.nexuslink.cyclenavi;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Rye on 2017/2/21.
 */

public class MyApplication extends Application{
    public static final String TAG = "My_TAG_RUN";
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
