package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.LookUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rye on 2017/3/5.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private List<String> articleImages;
    private Context context;

    public PhotosAdapter(Context context, List<String> articleImgs) {
        this.context = context;
        this.articleImages = articleImgs;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotosViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photos_published,null));
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        Log.d("TAG",articleImages.get(holder.getLayoutPosition()));
       /* Glide.with(context).load(articleImages.get(position)).into(holder.images);*/
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //需要将数组传入查看图片的activity
                Intent intent = new Intent(context, LookUpActivity.class);
                intent.putStringArrayListExtra("PICTURES", (ArrayList<String>) articleImages);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("TAG",articleImages.size()+"");
        return articleImages.size();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder{
        private ImageView images;
        public PhotosViewHolder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.images);
        }
    }
}
