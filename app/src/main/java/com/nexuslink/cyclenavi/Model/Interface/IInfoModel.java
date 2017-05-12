package com.nexuslink.cyclenavi.Model.Interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;

/**
 * Created by Rye on 2017/4/19.
 */

public interface IInfoModel {
    void storePic(Context context);

    Uri getUri();

    void addToGallery(Context context);

    void getScreenShot(final Bitmap bitmap, final ViewGroup viewContainer, final MapView mapView, final View...views);
}
