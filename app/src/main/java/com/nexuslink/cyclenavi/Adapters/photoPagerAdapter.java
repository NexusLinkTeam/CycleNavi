package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.LookUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rye on 2017/3/5.
 */
public class photoPagerAdapter extends PagerAdapter {
    private List<View> pictures;
    private Context context;
    public photoPagerAdapter(Context context, List<View> pictures) {
        this.pictures = pictures;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pictures.get(position));
        return pictures.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pictures.get(position));
    }
    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
