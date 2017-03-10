package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.PublishDialogActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rye on 2017/3/3.
 */
public class PhotosPrepareAdapter extends RecyclerView.Adapter<PhotosPrepareAdapter.PhotoPreparViewHoder>{
    private List<ImageItem> imageItems;
    private Context context;
    public PhotosPrepareAdapter(Context context) {
        this.context = context;
        this.imageItems = new ArrayList<>();
    }

    public void addPhoto(ArrayList<ImageItem> images){
        for(ImageItem image : images){
            imageItems.add(image);
        }
        notifyDataSetChanged();
    }

    @Override
    public PhotoPreparViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoPreparViewHoder(LayoutInflater.from(context).inflate(R.layout.item_photos,parent,false));
    }

    @Override
    public void onBindViewHolder(final PhotoPreparViewHoder holder, final int position) {
        Glide.with(context).load(imageItems.get(holder.getLayoutPosition()).path).into(holder.image);
        holder.delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageItems.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(position,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public class PhotoPreparViewHoder extends RecyclerView.ViewHolder {
        private ImageView delate;
        private ImageView image;
        public PhotoPreparViewHoder(View itemView) {
            super(itemView);
            delate = (ImageView) itemView.findViewById(R.id.delate);
            image = (ImageView) itemView.findViewById(R.id.myimage);
        }
    }
}
