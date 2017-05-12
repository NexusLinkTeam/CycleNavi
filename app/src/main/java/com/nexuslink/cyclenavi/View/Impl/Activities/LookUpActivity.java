package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nexuslink.cyclenavi.Adapters.PhotoPagerAdapter;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 暂时放弃
 */

public class LookUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        final ArrayList<String> pictures = getIntent().getStringArrayListExtra("PICTURES");
        ViewPager photoPager = (ViewPager) findViewById(R.id.photo_pager);

        photoPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));

        List<View> views = new ArrayList<>();
        for (String picture : pictures){
            View view = LayoutInflater.from(this).inflate(R.layout.item_image,null);
            final ProgressBar loadImg = (ProgressBar) view.findViewById(R.id.load_img);
            loadImg.setVisibility(View.VISIBLE);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.image_lookup);
            photoView.enable();
            Glide.with(LookUpActivity.this).load(picture).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    loadImg.setVisibility(View.GONE);
                    photoView.setImageDrawable(resource);
                }
            });
            views.add(view);
        }
        photoPager.setAdapter(new PhotoPagerAdapter(this,views));
    }
}
