package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;

import java.io.File;

/**
 * Created by Rye on 2017/4/19.
 */

public interface IInfoPresenter {
    void takePhoto(Context context);

    Uri getUri();

    void scan(Context context);

    void getShortCut(final Bitmap bitmap, final ViewGroup viewContainer, final MapView mapView, final View...views);

    void requestShortCutSuccess(File file);
}
