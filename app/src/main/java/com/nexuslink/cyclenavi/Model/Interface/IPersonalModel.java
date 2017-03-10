package com.nexuslink.cyclenavi.Model.Interface;

import android.net.Uri;

import com.nexuslink.cyclenavi.View.Impl.Activities.PersonalActivity;
import com.nexuslink.cyclenavi.View.Interface.IPersonalView;

/**
 * Created by Rye on 2017/1/25.
 */

public interface IPersonalModel {
    void cancelLoginStatus(PersonalActivity persoanalActivity);

    void upLoad(Uri uri, PersonalActivity personalActivity);
}
