package com.nexuslink.cyclenavi.Model.Interface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rye on 2017/3/28.
 */

public interface ISpeedModel {

   /* double currentSpeed();

    void storePic(Context context);*/

   /* Uri getUri();*/

    void uploadCurrent(RequestBody userId,
                       RequestBody totalTime,
                       RequestBody date,
                       RequestBody routeLine,
                       RequestBody speedList,
                       RequestBody heightList,
                       MultipartBody.Part picture);

/*
    void addToGallery(Context context);
*/
}
