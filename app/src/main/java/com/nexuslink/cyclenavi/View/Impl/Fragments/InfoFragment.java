package com.nexuslink.cyclenavi.View.Impl.Fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * 地图的fragment
 */
public class InfoFragment extends Fragment  implements LocationSource, AMapLocationListener, View.OnClickListener {
    private static InfoFragment instance;
    private MapView mapView;
    private AMap aMap;
    private AMapLocation aMapLocation;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private ISpeedPresenter presenter;


    @OnClick(R.id.backtospeed) void back(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getViewpager().setCurrentItem(0);
    }
    @OnClick(R.id.flag)void addFlag (){

    }

    @OnClick(R.id.search) void search(){

    }
    double lastLongitude;
    double lastAltitude;
    double lastLatitude;

    public final double EARTH_RADIUS = 6378.137;//地球半径
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    //测试
    List<Integer> colorList = new ArrayList<>();
    private PolylineOptions options ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this,view);
        getPermission();
        initView(view,savedInstanceState);
        return view;
    }
    private void initView(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.mapview);
        // 必须重写的方法
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setMinZoomLevel(15f);
        //监听定位，两个回调，激活与停止定位
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        //设置PolylineOptions
        colorList.add(Color.parseColor("#2196F3"));

        options = new PolylineOptions();
        options.width(20);//设置宽度

        ImageView location = (ImageView) view.findViewById(R.id.location);
        location.setOnClickListener(this);
    }

    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        aMap.setMapTextZIndex(2);
    }


    private void addPolylinesWithGradientColors(LatLng addedPoint) {


        options.add(addedPoint);

        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        options.colorValues(colorList);

        //加上这个属性，表示使用渐变线
        options.useGradient(true);

        aMap.addPolyline(options);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if(aMapLocationClient == null){
            //获得当前位置
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClientOption.setInterval(2000);
            aMapLocationClientOption.setSensorEnable(true);
            //模式可以写在设置里
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient = new AMapLocationClient(getContext());
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            aMapLocationClient.setLocationListener(this);
            aMapLocationClient.startLocation();
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 15));
        }
    }

    @Override
    public void deactivate() {
        locationChangedListener = null;
        if(aMapLocationClient != null){
            aMapLocationClient.startLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //一直发请求
        this.aMapLocation = aMapLocation;

       SpeedFragment speedFragment = (SpeedFragment) getFragmentManager().getFragments().get(0);
        speedFragment.setCurrentPosition(aMapLocation.getAoiName());
        if(locationChangedListener != null){
            double longitude = aMapLocation.getLongitude();//经度
            double altitude = aMapLocation.getAltitude();//高度
            double latitude = aMapLocation.getLatitude();//维度
            Log.d("TAG4",longitude+"curre");
            Log.d("TAG4",+aMapLocation.getSpeed()+"");
            /*new SpeedFragment().panView.setPercent((int) aMapLocation.getSpeed()/40);*/
            Intent intent = new Intent();
            intent.setAction("com.nexuslink.cyclenavi.speed");
            intent.putExtra("data",(int)aMapLocation.getSpeed());
            getContext().sendBroadcast(intent);
            lastLongitude = longitude;//经度
            lastAltitude = altitude;//高度
            lastLatitude = latitude;//维度

            //将需要存储的信息记录在数据库

            addPolylinesWithGradientColors(new LatLng(latitude,longitude));
            Log.d("TAG",longitude + "");
            locationChangedListener.onLocationChanged(aMapLocation);
        }
    }


    public static Fragment getInstance() {
        if(instance == null){
            return new InfoFragment();
        }
        return instance;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.location:
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()),18));
                break;
        }
    }
    //再想想这个算法？？？？？
    public static double getDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * 6378.137;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


}
