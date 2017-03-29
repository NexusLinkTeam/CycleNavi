package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anderson.dashboardview.view.DashboardView;
import com.nexuslink.cyclenavi.Presenter.Impl.SpeedPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.IntentUtil;
import com.nexuslink.cyclenavi.Util.TimeUtil;
import com.nexuslink.cyclenavi.View.Impl.Activities.DataActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.MainActivity;
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
    @BindView(R.id.timer)
    Chronometer textTimer;
    @BindView(R.id.position)
    TextView position;

    @OnClick(R.id.play) void play(){
        presenter.startCycle();
    }

    @OnClick(R.id.pause) void pause(){
        presenter.pauseCycle();
    }

    @OnClick(R.id.btn_map) void map(){
        IntentUtil.startActivity(getActivity(),DataActivity.class);
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
        textTimer.setTag(1);
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

    @Override
    public void showPause() {
        startVisible(true);
        new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("选择你的操作")
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //完成的逻辑

                    }
                }).setNegativeButton("休息一会儿", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startVisible(false);
                textTimer.setTag(0);
                textTimer.stop();
            }
        }).show();
    }

    @Override
    public void showStart() {
        startVisible(true);
        if(textTimer.getTag().equals(0)){
            new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("要继续开始吗？")
                    .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timeStart();
                        }
                    }).setNegativeButton("再休息一会儿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }else {
            timeStart();
        }
    }

    @Override
    public void showPage2() {
        //交给parent处理吧
        MainActivity parent  = (MainActivity) getActivity();
        parent.showPage(1);
    }

    private void timeStart() {
        textTimer.setBase(TimeUtil.convertStrTimeToLong(textTimer.getText().toString()));
        textTimer.start();//开始计时
    }

    private void startVisible(boolean b) {
        if(b){
            btnPlay.setVisibility(View.GONE);
            btnPause.setVisibility(View.VISIBLE);
        }else {
            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);
        }
    }

    public void setCurrentPosition(String aoiName) {
        if(aoiName != null){
            position.setText(aoiName);
        }
    }
}
