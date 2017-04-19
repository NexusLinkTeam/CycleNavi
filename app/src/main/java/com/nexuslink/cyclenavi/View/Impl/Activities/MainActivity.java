package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Adapters.MainFragmentStatePagerAdapter;
import com.nexuslink.cyclenavi.Presenter.Impl.MainPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.Constant;
import com.nexuslink.cyclenavi.Util.IntentUtil;
import com.nexuslink.cyclenavi.View.Impl.Fragments.InfoFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.SensorFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.SpeedFragment;
import com.nexuslink.cyclenavi.View.Interface.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/*
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView, LocationSource, AMapLocationListener, RouteSearch.OnRouteSearchListener, View.OnClickListener {
    private IMainPresenter iMainPresenter;
    private DrawerLayout drawer;
    private TextView text;
    private ToastBroadCast receiver;
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton fabNavi;
    private FloatingActionButton fabNaviRout;
    private AMapLocation aMapLocation;
    private LatLonPoint latLonPoint;
    private ImageView location;
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private TextView search;
    private TextView currentPosition;
    private TextView positionDetail;
    private RelativeLayout sheet1;
    private RelativeLayout sheet2;
    private RelativeLayout sheet3;
    private TextView currentPosition2;
    private TextView positionDetail2;
    private TextView currentPosition3;
    private TextView positionDetail3;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //权限申请
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},0);
        }

        //初始化部分
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView)headerView.findViewById(R.id.person_image);
        ImageView navi = (ImageView) findViewById(R.id.left_navi);
        ImageView imageView = (ImageView) findViewById(R.id.photo_search);

        iMainPresenter = new MainPresenterImpl(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_navi);
        text = (TextView) headerView.findViewById(R.id.name);
        text.setText(getSharedPreferences(Constant.spDataName,MODE_PRIVATE).getString("name",getString(R.string.without_login)));

        circleImageView.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fabNaviRout = (FloatingActionButton) findViewById(R.id.fab_navi3);
        fabNavi = (FloatingActionButton) findViewById(R.id.fab_navi2);
        location = (ImageView) findViewById(R.id.location);
        sheet1 = (RelativeLayout) findViewById(R.id.sheet1);
        sheet2 = (RelativeLayout) findViewById(R.id.sheet2);
        sheet3 = (RelativeLayout) findViewById(R.id.sheet3);
        search = (TextView) findViewById(R.id.search);
        currentPosition = (TextView) findViewById(R.id.current_position);
        positionDetail = (TextView)findViewById(R.id.position_detail);
        currentPosition2 = (TextView) findViewById(R.id.current_position2);
        positionDetail2 = (TextView)findViewById(R.id.position_detail2);
        currentPosition3 = (TextView) findViewById(R.id.current_position3);
        positionDetail3 = (TextView)findViewById(R.id.position_detail3);

        navi.setOnClickListener(this);
        imageView.setOnClickListener(this);

        location.setOnClickListener(this);
        search.setOnClickListener(this);
        fabNavi.setOnClickListener(this);
        fabNaviRout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.mapview);
        // 必须重写的方法
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setMapType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setMinZoomLevel(12f);
        //监听定位，两个回调，激活与停止定位
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
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



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
        if(locationChangedListener != null){
            locationChangedListener.onLocationChanged(aMapLocation);
            setBottomSheetText(aMapLocation.getStreet()+aMapLocation.getAoiName(),
                    aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict());
        }
    }

    private void setBottomSheetText(String detail,String around) {
        positionDetail.setText(detail);
        currentPosition.setText(around);
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
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        //返回骑车result
        RidePath ridePath =  rideRouteResult.getPaths().get(0);
        int dis = (int) ridePath.getDistance();
        int dur = (int) ridePath.getDuration();
        RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(
                this, aMap,ridePath,
                rideRouteResult.getStartPos(),
                rideRouteResult.getTargetPos());
        positionDetail3.setText("大约"+AMapUtil.getFriendlyTime(dur));
        currentPosition3.setText(AMapUtil.getFriendlyLength(dis));
        rideRouteOverlay.removeFromMap();
        rideRouteOverlay.addToMap();
        rideRouteOverlay.zoomToSpan();
        sheet2.setVisibility(View.GONE);
        sheet3.setVisibility(View.VISIBLE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            text.setText(getSharedPreferences("CycleNaviData",MODE_PRIVATE).getString("name",getString(R.string.without_login)));
            if(requestCode == 3){
                //防止与底部bottomsheet逻辑发生错乱
                aMap.clear();
                if(sheet2.getVisibility() == View.VISIBLE){
                    sheet2.setVisibility(View.GONE);
                    sheet1.setVisibility(View.VISIBLE);
                }else if (sheet3.getVisibility()== View.VISIBLE){
                    sheet3.setVisibility(View.GONE);
                    sheet2.setVisibility(View.VISIBLE);
                }

                search.setText(data.getStringExtra("DES"));
                setBottomSheetText(data.getStringExtra("DETAIL"),data.getStringExtra("AROUND"));
                aMap.addMarker(new MarkerOptions()
                        .position(new LatLng(data.getDoubleExtra("POINT_LATITUDE",0),data.getDoubleExtra("POINT_LONGITUDE",0)))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_end)));
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.getDoubleExtra("POINT_LATITUDE",0),data.getDoubleExtra("POINT_LONGITUDE",0)), 10));
                latLonPoint = new LatLonPoint(data.getDoubleExtra("POINT_LATITUDE",0),data.getDoubleExtra("POINT_LONGITUDE",0));
                sheet1.setVisibility(View.GONE);
                sheet2.setVisibility(View.VISIBLE);
                positionDetail2.setText(data.getStringExtra("DETAIL"));
                currentPosition2.setText(data.getStringExtra("AROUND"));
            }
        }

    }


    @Override
    public void onBackPressed() {
        Boolean checked = false;
        if (sheet3.getVisibility() == View.VISIBLE){
            sheet3.setVisibility(View.GONE);
            sheet1.setVisibility(View.VISIBLE);
            checked = true;
            aMap.clear();
        }
        if(!checked && sheet2.getVisibility() == View.VISIBLE){
            sheet2.setVisibility(View.GONE);
            sheet1.setVisibility(View.VISIBLE);
            checked = true;
            aMap.clear();
        }
        if(!checked && sheet1.getVisibility() == View.VISIBLE){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id){
            case R.id.msg:
                Intent intent1 = new Intent(MainActivity.this,MsgActivity.class);
                startActivity(intent1);
                break;
            case R.id.favorite:
                Intent intent2 = new Intent(MainActivity.this,FavouriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.forum:
                Intent intent3 = new Intent(MainActivity.this,ForumActivity.class);
                startActivity(intent3);
                break;
            case R.id.about:
                Intent intent4 = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent4);
                break;
            case R.id.exist:
                new AlertDialog.Builder(this).
                        setTitle(R.string.exist).
                setMessage(R.string.conform).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton(R.string.no,null).show();
                return true;
        }
        return true;
    }

    @Override
    public void ReplaceFragment(Fragment fragment) {

    }

    @Override
    public void SetExistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("确认退出？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("否",null).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.person_image:
                Boolean isLogin = getSharedPreferences("CycleNaviData",MODE_PRIVATE).getBoolean("isUserLogin",false);

                if(isLogin){
                    Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,0);
                }
                break;
            case R.id.left_navi:
            case R.id.photo_search:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.fab_navi:
            case R.id.search:

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivityForResult(intent,3);
                break;
            case R.id.location:
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()), 10));
                break;
            case R.id.fab_navi2:
                RouteSearch routeSearch = new RouteSearch(this);
                routeSearch.setRouteSearchListener(this);
                LatLonPoint mStartPoint = new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                LatLonPoint mEndPoint = latLonPoint;
                aMap.addMarker(new MarkerOptions()
                        .position(AMapUtil.convertToLatLng(mStartPoint))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_start)));
                aMap.addMarker(new MarkerOptions()
                        .position(AMapUtil.convertToLatLng(mEndPoint))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_end)));
                final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                        mStartPoint, mEndPoint);
                RouteSearch.RideRouteQuery query = new RouteSearch.RideRouteQuery(fromAndTo, RouteSearch.RIDING_FAST);
                routeSearch.calculateRideRouteAsyn(query);
                break;
            case R.id.fab_navi3:
                LatLonPoint mStartPoint1 = new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                LatLonPoint mEndPoint1 = latLonPoint;
                Toast.makeText(this,"测试",Toast.LENGTH_SHORT).show();
                Intent intent1 =  new Intent(MainActivity.this,NaviActivity.class);
                intent1.putExtra("START_LATITUDE",mStartPoint1.getLatitude());
                intent1.putExtra("START_LONGITUDE",mStartPoint1.getLongitude());
                intent1.putExtra("END_LATITUDE",mEndPoint1.getLatitude());
                intent1.putExtra("END_LONGITUDE",mEndPoint1.getLongitude());
                startActivity(intent1);
                break;

        }
    }



    private void bindVelocityMeasurement(){
        Intent intent = new Intent();
        intent.setAction("cyclenavi.Service.VelocityMeasurementService");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }
    private void initReceiver(){
        receiver = new ToastBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("cyclenavi.ToastReceiver");
        MainActivity.this.registerReceiver(receiver,filter);
    }

    public class ToastBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "请注意，您已超速", Toast.LENGTH_SHORT).show();
        }
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

}
*/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,IMainView {

    private IMainPresenter presenter;

    private TextView text;
    private CircleImageView circleImageView;
    private int Speed;

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    @BindView(R.id.bottom)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.viewpager_main)
    ViewPager mainPager;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        circleImageView.setOnClickListener(this);
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new SpeedFragment());
        fragments.add(InfoFragment.getInstance());
        fragments.add(new SensorFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.speed:
                        mainPager.setCurrentItem(0);
                        break;
                    case R.id.map:
                        mainPager.setCurrentItem(1);
                        break;
                    case R.id.sensor:
                        mainPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        mainPager.setOffscreenPageLimit(3);
        mainPager.setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager(),
                fragments));
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        View headerView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.person_image);
        text = (TextView) headerView.findViewById(R.id.name);
        text.setText(getSharedPreferences(Constant.spDataName,MODE_PRIVATE).
                getString("name",getString(R.string.without_login)));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
        new MainPresenterImpl(this);
        toolbar.setTitle(R.string.app_name);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id) {
            case R.id.msg:
                IntentUtil.startActivity(MainActivity.this, MsgActivity.class);
                break;
            case R.id.favorite:
                IntentUtil.startActivity(MainActivity.this, FavouriteActivity.class);
                break;
            case R.id.forum:
                IntentUtil.startActivity(MainActivity.this, ForumActivity.class);
                break;
            case R.id.about:
                IntentUtil.startActivity(MainActivity.this, AboutActivity.class);
                break;
            case R.id.exist:
                presenter.exit();
        }
        return true;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.person_image:
                presenter.moreUserInfo(this);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.finishLogin();
        }
    }

    /**
     * 设置presenter
     * @param presenter
     */

    @Override
    public void setPresenter(IMainPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 点击退出时
     */

    @Override
    public void showExitDialog() {
        new AlertDialog.Builder(this).
                setTitle(R.string.exist).
                setMessage(R.string.conform).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton(R.string.no, null).show();
    }

    /**
     * 处于登录状态
     */

    @Override
    public void showPersonZoom() {
        IntentUtil.startActivityForResult(MainActivity.this,PersonalActivity.class,1);
    }

    /**
     * 处于未登录状态
     */

    @Override
    public void showPrepareToLogin() {
        IntentUtil.startActivityForResult(MainActivity.this,LoginActivity.class,0);
    }

    /**
     * 登录完成后，展示userName
     */

    @Override
    public void showStatus() {
        text.setText(getSharedPreferences("CycleNaviData", MODE_PRIVATE).
                getString("name", getString(R.string.without_login)));
    }

    @Override
    public void showUserPhoto(Drawable personalImage) {

    }

    public void showPage(int position){
        mainPager.setCurrentItem(position);
    }

    public ViewPager getViewpager(){
        return mainPager;
    }
}
