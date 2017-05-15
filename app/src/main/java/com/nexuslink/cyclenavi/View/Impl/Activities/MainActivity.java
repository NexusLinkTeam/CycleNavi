package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nexuslink.cyclenavi.Adapters.MainFragmentStatePagerAdapter;
import com.nexuslink.cyclenavi.MyApplication;
import com.nexuslink.cyclenavi.Presenter.Impl.MainPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.IMainPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.IntentUtil;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.View.Impl.Fragments.HorizentalFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.InfoFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.SpeedFragment;
import com.nexuslink.cyclenavi.View.Interface.IFragCommunicate;
import com.nexuslink.cyclenavi.View.Interface.IMainView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,IMainView, IFragCommunicate {

    private static final int LOGIN = 0;
    private static final int PERSON = 1;
    private static final int RESULT_BACK = 10;
    private IMainPresenter presenter;
    private TextView text;
    private CircleImageView circleImageView;
    private SpeedFragment speedFragment;
    private InfoFragment infoFragment;
    private HorizentalFragment horizentalFragment;

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
        presenter = new MainPresenterImpl(this);
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
        speedFragment = new SpeedFragment();
        infoFragment = new InfoFragment();
        horizentalFragment = new HorizentalFragment();
        fragments.add(speedFragment);
        fragments.add(infoFragment);
        fragments.add(horizentalFragment);

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
        //从sp里获得姓名，没有就使用默认
        text.setText(SpUtil.getUserName());
        Log.d(MyApplication.TAG, "MainActivity:initView: "+"初始化：sp获得username:"+SpUtil.getUserName());
        if(SpUtil.getLoginStatus()){
            presenter.requestUserPic();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id) {
            case R.id.msg:
                if(!SpUtil.getLoginStatus()){
                    IntentUtil.startActivity(MainActivity.this, LoginActivity.class);
                }else {
                    IntentUtil.startActivity(MainActivity.this, MsgActivity.class);
                }
                break;
            case R.id.favorite:
                IntentUtil.startActivity(MainActivity.this, FavouriteActivity.class);
                break;
            case R.id.forum:
                if(!SpUtil.getLoginStatus()){
                    IntentUtil.startActivity(MainActivity.this, LoginActivity.class);
                }else {
                    IntentUtil.startActivity(MainActivity.this, ForumActivity.class);
                }
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
            switch (requestCode){
                case LOGIN:
                    presenter.requestUserPic();
                    presenter.finishLogin();
                    break;
                case PERSON:
                    text.setText("未登录");
                    circleImageView.setImageResource(R.drawable.default_photo);
                    break;
            }
        }else if (resultCode == RESULT_BACK){
            presenter.requestUserPic();
        }
    }

    @Override
    public void setPresenter(IMainPresenter presenter) {
        this.presenter = presenter;
    }

     //点击退出时
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

    //处于登录状态完成的跳转
    @Override
    public void showPersonZoom() {
        IntentUtil.startActivityForResult(MainActivity.this,PersonalActivity.class,PERSON);
    }


    //处于未登录状态完成的跳转
    @Override
    public void showPrepareToLogin() {
        IntentUtil.startActivityForResult(MainActivity.this,LoginActivity.class,LOGIN);
    }


    //登录完成后，展示userName
    @Override
    public void showStatus() {
        text.setText(SpUtil.getUserName());
        Log.d(MyApplication.TAG, "MainActivity:showStatus: "+"登录完成后：sp获得username:"+SpUtil.getUserName());
    }

    @Override
    public void showUserPhoto(String personalImage) {
        Glide.with(this).load(personalImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(circleImageView);
    }

    @Override
    public void loadView(String image) {
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(circleImageView);
    }

    //infoFragment开始绘制与计算
    @Override
    public void startDrawAndCalculate() {
        infoFragment.cancelTag();
    }

    //speedFragment展示速度
    @Override
    public void sendSpeed(float speed) {
        speedFragment.showSpeed(speed);
    }

    @Override
    public String getHeights() {
        return infoFragment.getHeights();
    }

    @Override
    public String getSpeeds() {
        return infoFragment.getSpeeds();
    }

    @Override
    public void getShortCut() {
        infoFragment.getShortCut();
    }

    @Override
    public String getTotalTime() {
        return speedFragment.getTotalTime();
    }

    @Override
    public void shortCutOK(File file) {
        speedFragment.upload(file);
    }

    @Override
    public void reset() {
        infoFragment.clear();
    }
}
