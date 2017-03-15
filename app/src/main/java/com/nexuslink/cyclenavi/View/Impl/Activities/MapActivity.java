package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends Activity implements LocationSource, AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private AMapLocation aMapLocation;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    //测试
    private List<LatLng> pointList = new ArrayList<>();
    List<Integer> colorList = new ArrayList<>();
    private PolylineOptions options ;
    /*private double Lat_A = 29.31167;
    private double Lon_A = 29.31167;

    private double Lat_B = 29.381265;
    private double Lon_B = 106.347152;

    private double Lat_C = 29.3879387;
    private double Lon_C = 106.37356;

    private double Lat_D = 29.3879313;
    private double Lon_D = 106.348896;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getPermission();



        mapView = (MapView) findViewById(R.id.mapview);
        // 必须重写的方法
        mapView.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setMinZoomLevel(17f);
        //监听定位，两个回调，激活与停止定位
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        //设置PolylineOptions
        colorList.add(Color.parseColor("#2196F3"));

        options = new PolylineOptions();
        options.width(20);//设置宽度
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
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
            //模式可以写在设置里
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient = new AMapLocationClient(this);
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            aMapLocationClient.setLocationListener(this);
            aMapLocationClient.startLocation();
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 10));
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
        if(locationChangedListener != null){
            double longitude = aMapLocation.getLongitude();//经度
            aMapLocation.getAltitude();//高度
            double latitude = aMapLocation.getLatitude();//维度

            //将需要存储的信息记录在数据库

            /*//测试存在内存
            list.add(new LatLng(longitude,latitude));
            addPolylinesWithGradientColors(list);
            Log.d("TAG",longitude + "");*/
            addPolylinesWithGradientColors(new LatLng(latitude,longitude));
            Log.d("TAG",longitude + "");
            locationChangedListener.onLocationChanged(aMapLocation);
        }

    }


}
