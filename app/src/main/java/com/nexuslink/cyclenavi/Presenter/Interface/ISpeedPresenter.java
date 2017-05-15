package com.nexuslink.cyclenavi.Presenter.Interface;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rye on 2017/3/28.
 */

public interface ISpeedPresenter {
    void startCycle();

    void pauseCycle(RequestBody userId,
                    RequestBody totalTime,
                    RequestBody date,
                    RequestBody routeLine,
                    RequestBody speedList,
                    RequestBody heightList,
                    MultipartBody.Part picture);

    void responseSuccess();
}
