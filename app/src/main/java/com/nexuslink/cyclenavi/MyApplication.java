package com.nexuslink.cyclenavi;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Rye on 2017/2/21.
 */

public class MyApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
