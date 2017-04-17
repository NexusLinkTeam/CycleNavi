package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nexuslink.cyclenavi.Adapters.photoPagerAdapter;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.List;

public class LookUpActivity extends AppCompatActivity {
    private ViewPager photoPager;
    private ProgressBar loadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ArrayList<String> pictures = getIntent().getStringArrayListExtra("PICTURES");
        photoPager = (ViewPager) findViewById(R.id.photo_pager);
        loadImg = (ProgressBar) findViewById(R.id.progress);

        photoPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        photoPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pictures.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                loadImg.setVisibility(View.VISIBLE);
                final PhotoView view = new PhotoView(LookUpActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(LookUpActivity.this).load(pictures.get(position)).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        loadImg.setVisibility(View.GONE);
                        view.setImageDrawable(resource);
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });



        /*List<View> views = new ArrayList<>();
        for (String picture : pictures){
            View view = LayoutInflater.from(this).inflate(R.layout.item_image,null);
            loadImg = (ProgressBar) view.findViewById(R.id.load_img);
            loadImg.setVisibility(View.VISIBLE);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.image_lookup);
// 启用图片缩放功能
            photoView.enable();
// 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
// 获取图片信息
            Info info = photoView.getInfo();
// 从普通的ImageView中获取Info
// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
            photoView.animaFrom(info);
// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照demo的使用
            photoView.animaTo(info,new Runnable() {
                @Override
                public void run() {
                    //动画完成监听
                }
            });
*//*//*/ /*获取/设置 动画持续时间
            photoView.setAnimaDuring(2000);
            int d = photoView.getAnimaDuring();
// 获取/设置 最大缩放倍数
            photoView.setMaxScale(float maxScale);
            float maxScale = photoView.getMaxScale();
// 设置动画的插入器
            photoView.setInterpolator(Interpolator interpolator);*//**//*
            Glide.with(this).load(picture).into(photoView);
            views.add(view);
            loadImg.setVisibility(View.GONE);

        }
        Log.d("TAG123",views.size()+"");

        photoPager.setAdapter(new photoPagerAdapter(this,views));
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }*/
    }
}
