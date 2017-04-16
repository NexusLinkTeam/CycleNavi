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
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.ForumActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.LookUpActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.PublishDialogActivity;

import java.util.List;

/**
 * Created by Rye on 2017/3/3.
 */
public class RecycleTopicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PUBLISH = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_BOTTOM_LOAD = 2;
    private Context context;
    private List<FreshBean.ArticlesBean> articles;
    private onTopicClickListener onTopicClickListener;

    public List<FreshBean.ArticlesBean> getArticles() {
        return articles;
    }

    public void setOnTopicClickListener(RecycleTopicsAdapter.onTopicClickListener onTopicClickListener) {
        this.onTopicClickListener = onTopicClickListener;
    }

    public RecycleTopicsAdapter(Context context, List<FreshBean.ArticlesBean> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_PUBLISH;
        }else if(position == articles.size() + 1){
            return TYPE_BOTTOM_LOAD;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_PUBLISH){
            return new PublishViewHolder(LayoutInflater.from(context).inflate(R.layout.item_publish,parent,false));
        }else if(viewType == TYPE_NORMAL){
            return new TopicsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topics,parent,false));
        }else if(viewType == TYPE_BOTTOM_LOAD){
            return new BottomLoadHolder(LayoutInflater.from(context).inflate(R.layout.item_bottom_load,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TopicsViewHolder){
            ((TopicsViewHolder) holder).userName.setText(articles.get(position - 1).getUser().getUserName());
            ((TopicsViewHolder) holder).content.setText(articles.get(position - 1).getArticleContent());
/*
            Glide.with(context).load(articles.get(position-1).getUser().getUserImg()).into(((TopicsViewHolder) holder).userPhoto);
*/

            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);

            ((TopicsViewHolder) holder).photos.setLayoutManager(manager);
            ((TopicsViewHolder) holder).photos.setAdapter(new PhotosAdapter(context, (List<String>) articles.get(holder.getLayoutPosition() - 1).getArticleImgs()));

            ((TopicsViewHolder) holder).photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LookUpActivity.class);
                    context.startActivity(intent);
                }
            });
            ((TopicsViewHolder) holder).time.setText(articles.get(position - 1).getTime());

            ((TopicsViewHolder) holder).like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onlikeClicked(view);
                }
            });
            ((TopicsViewHolder) holder).comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onCommentClicke();
                }
            });
            ((TopicsViewHolder) holder).userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onOthersPhotoClick();
                }
            });


        }else if(holder instanceof PublishViewHolder){
            ((PublishViewHolder) holder).publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,PublishDialogActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return articles.size()+ 2;
    }

    public void loadMore(List<FreshBean.ArticlesBean> addarticles) {
            articles.addAll(addarticles);
            notifyDataSetChanged();
    }

    public void removeAll() {
        articles.clear();
        notifyDataSetChanged();
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

    private class PublishViewHolder extends RecyclerView.ViewHolder {
        private CardView publish;

        public PublishViewHolder(View itemView) {
            super(itemView);
            publish = (CardView) itemView.findViewById(R.id.publish);
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
