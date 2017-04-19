package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Rye on 2017/4/19.
 */

public interface IInfoPresenter {
    void takePhoto(Context context);

    Uri getUri();

    void scan(Context context);
}
