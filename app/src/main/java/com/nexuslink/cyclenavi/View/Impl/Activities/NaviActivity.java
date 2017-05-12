package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.navi.AMapHudView;
import com.amap.api.navi.AMapHudViewListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.nexuslink.cyclenavi.R;

/**
 * 暂时放弃
 */

public class NaviActivity extends AppCompatActivity implements  AMapNaviListener, AMapHudViewListener {
    private AMapHudView aMapNaviView;
    private AMapNavi mAMapNavi;
    private TextView nowSpped, averSpeed,kcal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        aMapNaviView = (AMapHudView) findViewById(R.id.amap_navi);
        nowSpped = (TextView) findViewById(R.id.text_now_speed);
        averSpeed = (TextView) findViewById(R.id.text_aver_speed);
        kcal = (TextView) findViewById(R.id.text_kcal);

        mAMapNavi.addAMapNaviListener(this);
        aMapNaviView.setHudMenuEnabled(false);
        aMapNaviView.setHudViewListener(this);
        mAMapNavi.setBroadcastMode(MODE_APPEND);
    }

    @Override
    protected void onResume() {
        super.onResume();
        aMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        aMapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapNaviView.onDestroy();
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateRideRoute(new NaviLatLng(getIntent().getDoubleExtra("START_LATITUDE",0)
        ,getIntent().getDoubleExtra("START_LONGITUDE",0)),new NaviLatLng(getIntent().getDoubleExtra("END_LATITUDE",0),
                getIntent().getDoubleExtra("END_LONGITUDE",0)));
        Log.d("TAG",getIntent().getDoubleExtra("START_LATITUDE",0) + ""+
                getIntent().getDoubleExtra("START_LONGITUDE",0)+""+
                getIntent().getDoubleExtra("END_LATITUDE",0)+
        getIntent().getDoubleExtra("END_LONGITUDE",0));
        mAMapNavi.startNavi(NaviType.GPS);

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        Float speed  = aMapNaviLocation.getSpeed();
        Log.d("TAG123",speed +"速度");
    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

    }

    @Override
    public void onArriveDestination(AMapNaviStaticInfo aMapNaviStaticInfo) {
    }

    //算路成功回调，开始导航
    @Override
    public void onCalculateRouteSuccess() {
        mAMapNavi.startGPS();
        mAMapNavi.setIsUseExtraGPSData(true);

        Log.d("TAG123",mAMapNavi.isGpsReady()+"");
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onHudViewCancel() {

    }
}
