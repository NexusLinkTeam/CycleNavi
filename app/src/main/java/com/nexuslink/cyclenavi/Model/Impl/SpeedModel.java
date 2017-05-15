package com.nexuslink.cyclenavi.Model.Impl;

import com.nexuslink.cyclenavi.Extra.Net.ApiService;
import com.nexuslink.cyclenavi.Extra.Net.RetrofitClient;
import com.nexuslink.cyclenavi.Model.Interface.ISpeedModel;
import com.nexuslink.cyclenavi.Model.JavaBean.SaveRouteBean;
import com.nexuslink.cyclenavi.Presenter.Interface.ISpeedPresenter;
import com.nexuslink.cyclenavi.Util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rye on 2017/3/28.
 */

public class SpeedModel implements ISpeedModel {

    private ISpeedPresenter presenter;

    public SpeedModel(ISpeedPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    // TODO: 2017/5/8 待测试
    //上传骑行路线
    public void uploadCurrent(RequestBody userId,
                                RequestBody totalTime,
                                RequestBody date,
                                RequestBody routeLine,
                                RequestBody speedList,
                                RequestBody heightList,
                                MultipartBody.Part picture){
        RetrofitClient.create(ApiService.class)
                .saveRout(userId,totalTime,date,routeLine,speedList,heightList,picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveRouteBean>() {
                    @Override
                    public void accept(@NonNull SaveRouteBean saveRouteBean) throws Exception {
                        ToastUtil.shortToast("上传行车记录");
                        presenter.responseSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.shortToast(throwable.toString());
                    }
                });
    }

}
