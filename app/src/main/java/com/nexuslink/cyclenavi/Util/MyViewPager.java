package com.nexuslink.cyclenavi.Util;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Rye on 2017/3/20.
 */

public class MyViewPager extends ViewPager {

        public MyViewPager(Context context) {
            super(context);
        }

        public MyViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
            if(v.getClass().getName().equals("com.amap.api.maps.MapView")) {
                return true;
            }
            //if(v instanceof MapView){
            //    return true;
            //}
            return super.canScroll(v, checkV, dx, x, y);
        }
    }
