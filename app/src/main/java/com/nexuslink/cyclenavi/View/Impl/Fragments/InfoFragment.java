package com.nexuslink.cyclenavi.View.Impl.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.nexuslink.cyclenavi.Presenter.Impl.InfoPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.IInfoPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.ToastUtil;
import com.nexuslink.cyclenavi.View.Interface.IFragCommunicate;
import com.nexuslink.cyclenavi.View.Interface.IInfoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;

/**
 * 地图fragment
 */
public class InfoFragment extends Fragment  implements LocationSource, AMapLocationListener, View.OnClickListener ,IInfoView,AMap.OnMapScreenShotListener{
    private static final int NEED_CAMERA = 0;
    private static final int TAKE_PHOTO = 1;
    private static final float MIN_ZOOM_LEVEL = 15f;
    private static final int LOCATION = 2;
    private MapView mapView;
    private AMap aMap;
    private AMapLocation aMapLocation;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private IInfoPresenter presenter;
    private Boolean tag = false;
    private IFragCommunicate call;
    private PolylineOptions options ;
    private StringBuilder speeds = new StringBuilder();
    private StringBuilder heights = new StringBuilder();

    //拍照
    @OnClick(R.id.btn_take_photo) void takePhoto(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==  PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
            presenter.takePhoto(getContext());
        }else {
            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},NEED_CAMERA);
        }
    }

    @BindView(R.id.container)
    LinearLayout container;

    List<Integer> colorList = new ArrayList<>();//实际上只用了一种颜色

    //获取权限初始化view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this,view);
        getPermission();
        initView(view,savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        presenter = new InfoPresenterImpl(this);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setMinZoomLevel(MIN_ZOOM_LEVEL);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        //绘制路线设置
        colorList.add(Color.parseColor("#2196F3"));
        options = new PolylineOptions();
        options.width(20);

        ImageView location = (ImageView) view.findViewById(R.id.location);
        location.setOnClickListener(this);
    }

    //设置地图视野
    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        aMap.setMapTextZIndex(2);
    }

    private void addPolylinesWithGradientColors(LatLng addedPoint) {
        options.add(addedPoint);
        options.colorValues(colorList);
        options.useGradient(true);
        aMap.addPolyline(options);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        call = (IFragCommunicate) context;
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

    //拍照所需要的权限
    private void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},LOCATION);
        }
    }

    //权限获取后的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION){
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if(aMapLocationClient == null){
            AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClientOption.setInterval(2000);
            aMapLocationClientOption.setSensorEnable(true);
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
        this.aMapLocation = aMapLocation;
        // TODO: 2017/5/9 tag
        if(locationChangedListener != null && tag){
            double longitude = aMapLocation.getLongitude();//经度
            double latitude = aMapLocation.getLatitude();//维度
            call.sendSpeed(aMapLocation.getSpeed());
            addPolylinesWithGradientColors(new LatLng(latitude,longitude));//绘制
            speeds.append(aMapLocation.getSpeed()+",");
            heights.append(aMapLocation.getLongitude()+",");
        }
        assert locationChangedListener != null;
        locationChangedListener.onLocationChanged(aMapLocation);
    }

    //当前位置
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.location:
                if(aMapLocation != null )
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()),18));
                break;
        }

    }

    //打开摄像头
    @Override
    public void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = presenter.getUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    public void showShortCut(File file) {
        call.shortCutOK(file);
    }

    //摄像头打开后的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
                    presenter.scan(getContext());
                    Toast.makeText(getContext(),"图片已保存到'我的骑行相册'",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 改变标志位，向speed发送速度信息
     */
    public void cancelTag() {
        tag = true;
    }

    // TODO: 2017/5/9  返回骑行完成后的速度表
    public String getSpeeds() {
/*
        tag = false;// 取消绘制与定位
*/
/*
        speeds = new StringBuilder("");//清空
*/
        return speeds.toString();
       /* return "";*/
    }

    // TODO: 2017/5/9  返回完成骑行后的截图
    public void getShortCut() {
        tag = false;//取消绘制与定位
        aMap.getMapScreenShot(this);
/*
        return new File(Environment.getExternalStorageDirectory()+"/DCIM/100PINT/Pins/pic.png");
*/
    }


    public String getHeights() {
        return heights.toString();
    }

    @Override
    public void onMapScreenShot(Bitmap bitmap) {
        ToastUtil.shortToast("开始");
        presenter.getShortCut(bitmap,container,mapView);
    }

    @Override
    public void onMapScreenShot(Bitmap bitmap, int i) {

    }
}
