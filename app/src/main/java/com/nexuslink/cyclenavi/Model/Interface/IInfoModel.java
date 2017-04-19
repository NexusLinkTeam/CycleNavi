package com.nexuslink.cyclenavi.Model.Interface;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Rye on 2017/4/19.
 */

public interface IInfoModel {
    void storePic(Context context);

    Uri getUri();

    void addToGallery(Context context);
}
