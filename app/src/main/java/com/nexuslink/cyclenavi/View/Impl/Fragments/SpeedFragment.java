package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import static android.app.Activity.RESULT_OK;

/**
 * 主要是展示仪表盘的一个fragment
 * Created by Rye on 2017/3/20.
 */

public class SpeedFragment extends Fragment implements ISpeedView {
    private static final int NEED_CAMERA = 0;
    private static final int TAKE_PHOTO = 1;
    private ISpeedPresenter presenter;
    private SpeedFragment instance;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int speed = intent.getIntExtra("data",0);
            Log.d("TAG4",speed + "");
            panView.setPercent(speed * 100 / 40);
        }
    };

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
        Snackbar.make(panView,"开始骑行”",Snackbar.LENGTH_SHORT)
                .show();
        presenter.startCycle();
    }

    @OnClick(R.id.pause) void pause(){
        presenter.pauseCycle();
    }

    @OnClick(R.id.btn_map) void map(){
        IntentUtil.startActivity(getActivity(),DataActivity.class);
    }

    @OnClick(R.id.btn_take_photo) void takePhoto(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==  PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
            //权限是获得的
            presenter.takePhoto(getContext());
        }else {
            //未获得权限
            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},NEED_CAMERA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this,view);
        IntentFilter filter = new IntentFilter("com.nexuslink.cyclenavi.speed");
        getContext().registerReceiver(broadcastReceiver, filter);
        initData();
        return view;
    }

    public SpeedFragment getInstance() {
        if(instance == null){
            instance = new SpeedFragment();
        }
        return instance;
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

    @Override
    public void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = presenter.getUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
                    presenter.scan(getContext());
                    /*new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("图片已保存到我的“骑行相册”")
                            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    timeStart();
                                }
                            }).setNegativeButton("分享", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();*/
                    Snackbar.make(panView,"图片已保存到我的“骑行相册”",Snackbar.LENGTH_SHORT)
                    .setAction("分享", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //分享的操作
                        }
                    }).show();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}
