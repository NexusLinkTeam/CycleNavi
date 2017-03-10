package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexuslink.cyclenavi.Adapters.photoPagerAdapter;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.List;

public class LookUpActivity extends AppCompatActivity {
    private ViewPager photoPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> pictures = getIntent().getStringArrayListExtra("PICTURES");
        photoPager = (ViewPager) findViewById(R.id.photo_pager);
        List<View> views = new ArrayList<>();
        for (String picture : pictures){
            View view = LayoutInflater.from(this).inflate(R.layout.item_image,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_lookup);
            Glide.with(this).load(picture).into(imageView);
            views.add(view);
        }
        Log.d("TAG123",views.size()+"");

        photoPager.setAdapter(new photoPagerAdapter(this,views));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}
