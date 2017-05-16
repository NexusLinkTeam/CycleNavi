package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Adapters.PersonInfoPageAdapter;
import com.nexuslink.cyclenavi.Presenter.Impl.PersonalPresenterImpl;
import com.nexuslink.cyclenavi.R;

import com.nexuslink.cyclenavi.View.Impl.Fragments.PersonInfoFragment;
import com.nexuslink.cyclenavi.View.Impl.Fragments.PersonRideRouteFragment;

import com.nexuslink.cyclenavi.Util.SpUtil;

import com.nexuslink.cyclenavi.View.Interface.IPersonalView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;


public class PersonalActivity extends AppCompatActivity implements View.OnClickListener,IPersonalView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.name_text)
    TextView name;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.personInfo_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingtoolbarlayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.personInfo_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.personInfo_vg)
    ViewPager mViewPager;


    private static final int RESULT_BACK = 10;

    private PersonalPresenterImpl personalPresenterImpl;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"我的帖子","我的骑行"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        ButterKnife.bind(this);
        personalPresenterImpl = new PersonalPresenterImpl(this);
        name.setText(getSharedPreferences("CycleNaviData",MODE_PRIVATE).getString("name","name"));

        TextView name = (TextView) findViewById(R.id.name_text);
        name.setText(SpUtil.getUserName());
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView logout = (TextView) findViewById(R.id.logout);
        ImageView photo = (ImageView) findViewById(R.id.photo);

        photo.setOnClickListener(this);
        back.setOnClickListener(this);
        logout.setOnClickListener(this);
        initFragments();
        initView();
    }
    private void initView()
    {
        this.setSupportActionBar(mToolbar);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new PersonInfoPageAdapter(getSupportFragmentManager(),mFragments,mTitles));
        mTabLayout.setTabTextColors(Color.WHITE,Color.WHITE);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void initFragments()
    {
        mFragments = new ArrayList<>();
        mFragments.add(PersonInfoFragment.getInstance(mTitles[0]));
       /* mFragments.add(PersonInfoFragment.getInstance(mTitles[1]));*/
        mFragments.add(PersonRideRouteFragment.getInstance(mTitles[1]));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.logout:
                new AlertDialog.Builder(this).setTitle(getString(R.string.logout))
                        .setMessage(R.string.logout_message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        personalPresenterImpl.logout();
                        setResult(RESULT_OK);
                        finish();
                    }
                }).setNegativeButton(R.string.no,null).show();
                break;
            case R.id.photo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choosePath)
                        .setItems(new String[]{"相机", "图库"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        //相机拍摄
                                        break;
                                    case 1:
                                        Intent intent = new Intent(
                                                Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, 1);
                                        break;
                                }
                            }
                        }).show();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            personalPresenterImpl.upLoadPhoto(uri,this);
        }
    }
    @Override
    public PersonalActivity getCurrentActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_BACK);
        finish();
    }
}
