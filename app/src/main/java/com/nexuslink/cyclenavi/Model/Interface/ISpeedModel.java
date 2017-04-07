package com.nexuslink.cyclenavi.Model.Interface;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Rye on 2017/3/28.
 */

public interface ISpeedModel {

    double currentSpeed();

    void storePic(Context context);

    Uri getUri();

    void addToGallery(Context context);
}
