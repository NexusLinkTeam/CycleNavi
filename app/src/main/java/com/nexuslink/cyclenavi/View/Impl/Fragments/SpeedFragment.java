package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.anderson.dashboardview.view.DashboardView;
import com.nexuslink.cyclenavi.Presenter.Impl.SpeedPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.TimeUtil;
import com.nexuslink.cyclenavi.View.Interface.IFragCommunicate;
import com.nexuslink.cyclenavi.View.Interface.ISpeedView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主要是展示仪表盘的一个fragment
 * Created by Rye on 2017/3/20.
 */

public class SpeedFragment extends Fragment implements ISpeedView {
    private static final float MAX_SIZE = 40;
    private static final int PAN_TEXT_SIZE = 35;
    private ISpeedPresenter presenter;
    private IFragCommunicate call;

    @BindView(R.id.panView)
    DashboardView panView;//仪表盘
    @BindView(R.id.play)
    FloatingActionButton btnPlay;//开始按钮
    @BindView(R.id.finish)
    FloatingActionButton btnFinish;//完成按钮
    @BindView(R.id.timer)
    Chronometer textTimer;//计时器

    @OnClick(R.id.play) void play(){
        // TODO: 2017/4/28  讯飞语音“开始骑行”
        Snackbar.make(panView,"开始骑行”",Snackbar.LENGTH_SHORT)
                .show();
        //=====================================
        presenter.startCycle();// =====> 最终执行showStart()
    }

    @OnClick(R.id.finish) void pause(){
        presenter.pauseCycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */

    private void initData() {
        panView.setTextSize(PAN_TEXT_SIZE);
        panView.setMaxNum(MAX_SIZE);
        panView.setUnit("公里/小时");
        presenter = new SpeedPresenterImpl(this);
    }

    @Override
    public void setPresenter(ISpeedPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showFinish() {

    }

    /**
     * 开始按钮点击后执行的方法，
     * 通知infofragment开始绘制与计算
     * 按钮切换
     * 计时开始
     */

    @Override
    public void showStart() {
        call.startDrawAndCalculate();
        startVisible(true);
        timeStart();
    }

    /**
     * 开始记录时间
     */

    private void timeStart() {
        textTimer.setBase(TimeUtil.convertStrTimeToLong(textTimer.getText().toString()));
        textTimer.start();//开始计时
    }

    /**
     * 设置开始与结束时按钮图标变化
     * @param b 播放按钮是否可见
     */

    private void startVisible(boolean b) {
        if(b){
            btnPlay.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        }else {
            btnPlay.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        call = (IFragCommunicate) context;
    }

    public void showSpeed(float speed) {
        panView.setPercent((int) (speed / MAX_SIZE * 100));
    }
}
