package com.nexuslink.cyclenavi.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 教科书式的机智少年 on 2017/2/17.
 * 这是测速服务，经过讨论，应当在开启导航时就开启
 */

public class VelocityMeasurementService extends Service implements AMapLocationListener {
    //经过学习，发现实现测速的话，通过GPS实现更加现实，所以还是通过GPS实现测速
    private Double longitudeOnce;
    private Double latitudeOnce;
    private Double longitudeTwice;
    private Double latitudeTwice;
    private static Double EARTH_RADIUS = 6371.0;//km 地球半径 平均值，千米
    private AMapLocationClient client;
    private AMapLocationClientOption options;
    private int type = 0;
    private Timer timer;
    //每隔10s测试一次速度，超速则发送广播
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (longitudeOnce != null && latitudeOnce!= null && longitudeTwice != null && latitudeOnce != null){
                //Double rusoult = Math.pow(Math.pow(longitudeOnce-longitudeTwice,2)+Math.pow(latitudeOnce-latitudeTwice,2),1.0/2);
                LatLng latLngOnce = new LatLng(latitudeOnce,longitudeOnce);
                LatLng latLngTwice = new LatLng(latitudeTwice,longitudeTwice);
                float distance = AMapUtils.calculateLineDistance(latLngOnce,latLngTwice);
                float spead = distance/10;
                if (spead>8.3){
                    Intent intent = new Intent();
                    intent.setAction("cyclenavi.ToastReceiver");
                    sendBroadcast(intent);
                }
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new AMapLocationClient(getApplicationContext());
        options = new AMapLocationClientOption();
        options.setOnceLocationLatest(true);
        options.setInterval(10000);
        client.setLocationOption(options);
        timer.schedule(task,10000,10000);
        client.startLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            //可在其中解析amapLocation获取相应内容。
            if (type == 0){
                longitudeOnce = aMapLocation.getLongitude();
                latitudeOnce = aMapLocation.getLatitude();
                type = 1;
            }else {
                longitudeTwice = aMapLocation.getLongitude();
                latitudeTwice = aMapLocation.getLatitude();
                type = 0;
            }

        }else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError","location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }
}
