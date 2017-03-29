package com.nexuslink.cyclenavi.Util;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;
import com.nexuslink.cyclenavi.R;

import java.io.File;

public class GlideImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

            Glide.with(activity)//
                    .load(new File(path))//
                    .placeholder(R.color.black)//
                    .error(R.color.black)//
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }