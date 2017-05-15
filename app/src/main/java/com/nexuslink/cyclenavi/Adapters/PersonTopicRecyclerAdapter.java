package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Model.JavaBean.HitsBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.LookUpActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.PublishDialogActivity;

import java.util.List;

/**
 * Created by G150S on 2017/4/29.
 */

public class PersonTopicRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private String TAG = "PersTpcRecyclerAdapter";
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_BOTTOM_LOAD = 2;
    private Context context;
    private List<HitsBean.ArticlesBean> articles;
    private RecycleTopicsAdapter.onTopicClickListener onTopicClickListener;

    public List<HitsBean.ArticlesBean> getArticles() {
        return articles;
    }

    public void setOnTopicClickListener(RecycleTopicsAdapter.onTopicClickListener onTopicClickListener) {
        this.onTopicClickListener = onTopicClickListener;
    }

    public PersonTopicRecyclerAdapter(Context context, List<HitsBean.ArticlesBean> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == articles.size()){
            return TYPE_BOTTOM_LOAD;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType == TYPE_NORMAL){
            return new PersonTopicRecyclerAdapter.TopicsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topics,parent,false));
        }else if(viewType == TYPE_BOTTOM_LOAD){
            return new PersonTopicRecyclerAdapter.BottomLoadHolder(LayoutInflater.from(context).inflate(R.layout.item_bottom_load,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PersonTopicRecyclerAdapter.TopicsViewHolder){
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).userName.setText(articles.get(position).getUser().getUserName());
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).content.setText(articles.get(position).getArticleContent());
            Glide.with(context).load(articles.get(position).getUser().getUserImg()).into(((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).userPhoto);

            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);

            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).photos.setLayoutManager(manager);
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).photos
                    .setAdapter(new PhotosAdapter(context, (List<String>) articles.get(holder.getLayoutPosition()).getArticleImgs()));

            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LookUpActivity.class);
                    context.startActivity(intent);
                }
            });
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).time.setText(articles.get(position).getTime());

            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onlikeClicked(view);
                }
            });
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onCommentClicke();
                }
            });
            ((PersonTopicRecyclerAdapter.TopicsViewHolder) holder).userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onOthersPhotoClick();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return articles.size()+ 1;
    }

    public void loadMore(List<HitsBean.ArticlesBean> addarticles) {
        Log.d("TAG","Article size："+articles.size()+"");
        articles.addAll(addarticles);
        notifyDataSetChanged();
    }

    public void removeAll() {
        articles.clear();
        Log.e(TAG,articles.size()+"remoeAll后的数量");
    }

    public class TopicsViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView content;
        private ImageView userPhoto;
        private RecyclerView photos;
        private TextView time;
        private ImageView like;
        private ImageView comment;

        public TopicsViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.user_content);
            userPhoto = (ImageView) itemView.findViewById(R.id.user_photo);
            photos = (RecyclerView) itemView.findViewById(R.id.recycle_photos);
            time = (TextView) itemView.findViewById(R.id.article_time);
            like = (ImageView) itemView.findViewById(R.id.like);
            comment = (ImageView) itemView.findViewById(R.id.comment);
        }
    }

    public class BottomLoadHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView info;
        public BottomLoadHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.btom_load);
            info = (TextView) itemView.findViewById(R.id.btom_text);
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public TextView getInfo() {
            return info;
        }
    }

    public interface onTopicClickListener{
        void onlikeClicked(View view);
        void onCommentClicke();
        void onOthersPhotoClick();
    }
}
