package com.nexuslink.cyclenavi.Util;

import android.util.Log;
import android.view.View;

/**
 * Created by Rye on 2017/1/22.
 */

public class ShowProgressTool {
    public static void showProgress(Boolean ishow, View progressView, View view){
        //没有动画
        view.setVisibility(ishow? View.GONE:View.VISIBLE);
        progressView.setVisibility(ishow? View.VISIBLE:View.GONE);
        Log.d("ShowPrpgressTool","设置可见");
    }
}
