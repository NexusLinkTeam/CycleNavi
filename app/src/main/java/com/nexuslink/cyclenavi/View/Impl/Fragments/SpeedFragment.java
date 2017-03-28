package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.anderson.dashboardview.view.DashboardView;
import com.nexuslink.cyclenavi.BasePresenter;
import com.nexuslink.cyclenavi.BaseView;
import com.nexuslink.cyclenavi.Presenter.Impl.SpeedPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Interface.ISpeedView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主要是展示仪表盘的一个fragment
 * Created by Rye on 2017/3/20.
 */

public class SpeedFragment extends Fragment implements ISpeedView {
    private ISpeedPresenter presenter;

    @BindView(R.id.panView)
    DashboardView panView;
    @BindView(R.id.play)
    RelativeLayout btnPlay;
    @BindView(R.id.pause)
    RelativeLayout btnPause;

    @OnClick(R.id.play) void play(){
        btnPlay.setVisibility(View.GONE);
        btnPause.setVisibility(View.VISIBLE);
        /*timer.setBase(SystemClock.elapsedRealtime());//计时器清零*//*
                timer.setBase(convertStrTimeToLong(timer.getText().toString()));
                timer.start();//开始计时*/
    }

    @OnClick(R.id.pause) void pause(){
        btnPlay.setVisibility(View.GONE);
        btnPause.setVisibility(View.VISIBLE);
        /*new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("选择你的操作")
                        .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //完成的逻辑
                            }
                        }).setNegativeButton("休息一会儿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnPlay.setVisibility(View.VISIBLE);
                        btnPause.setVisibility(View.GONE);
                        timer.stop();
                    }
                }).show();*/
    }

    @OnClick(R.id.btn_map) void map(){

    }

    @OnClick(R.id.btn_take_photo) void takePhoto(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData() {
        panView.setTextSize(35);
        panView.setUnit("公里/小时");
        presenter = new SpeedPresenterImpl(this);
    }

    @Override
    public void setPresenter(ISpeedPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCurrentSpeed(int persent) {
        panView.setPercent(persent);
    }
}
