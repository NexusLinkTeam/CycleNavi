package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.View.Impl.Activities.LookUpActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.PublishDialogActivity;

import java.util.ArrayList;
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
    private int lastPosition = 0;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TopicsViewHolder){
            Log.d("MY_TAG",articles.get(position - 1).getUser().getUserName());
            ((TopicsViewHolder) holder).userName.setText(articles.get(position - 1).getUser().getUserName());
            ((TopicsViewHolder) holder).content.setText(articles.get(position - 1).getArticleContent());
            Glide.with(context).load(articles.get(position-1).getUser().getUserImg())
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((TopicsViewHolder) holder).userPhoto);

            List<String> list = articles.get(position - 1).getArticleImgs();
           if(list.size() != 0){
               ((TopicsViewHolder) holder).img_relative.setVisibility(View.VISIBLE);
               ((TopicsViewHolder) holder).img_relative.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(context, LookUpActivity.class);
                       intent.putStringArrayListExtra("PICTURES", (ArrayList<String>) articles.get(position - 1).getArticleImgs());
                       context.startActivity(intent);
                   }
               });
               Glide.with(context).load(list.get(0)).into(((TopicsViewHolder) holder).photos);
           }else {
               ((TopicsViewHolder) holder).img_relative.setVisibility(View.GONE);
           }
            ((TopicsViewHolder) holder).time.setText(articles.get(position - 1).getTime());
            if(position > lastPosition){
                showAnim(((TopicsViewHolder) holder).cardView,position);
            }

            Boolean isLike = articles.get(position - 1).isLikeArticle();
            if(isLike){
                ((TopicsViewHolder) holder).like.setImageDrawable(context.getDrawable(R.drawable.like));
            }else {
                ((TopicsViewHolder) holder).like.setImageDrawable(context.getDrawable(R.drawable.unlike));
            }
            ((TopicsViewHolder) holder).numLike.setText(String.valueOf(articles.get(position - 1).getLikeNum()));
            ((TopicsViewHolder) holder).numRepaly.setText(String.valueOf(articles.get(position - 1).getCommentNum()));

            ((TopicsViewHolder) holder).like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTopicClickListener.onlikeClicked(((TopicsViewHolder) holder).numLike,v,holder.getLayoutPosition());
                }
            });
            ((TopicsViewHolder) holder).comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onCommentClicke(articles.get(position - 1).getArticleId());
                }
            });
            ((TopicsViewHolder) holder).userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicClickListener.onOthersPhotoClick();
                }
            });


        }else if(holder instanceof PublishViewHolder){
            //sp
            Glide.with(context).load(SpUtil.getUserImage()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((PublishViewHolder) holder).image);
            ((PublishViewHolder) holder).publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,PublishDialogActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void showAnim(final CardView cardView, int position) {
        Log.d("POSITION",position+"Position");
        Log.d("POSITION",lastPosition+"LastPosition");
        Animation anim = AnimationUtils.loadAnimation(context,R.anim.my_anim);
        cardView.setAnimation(anim);
        lastPosition = position;
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
        private ImageView photos;
        private TextView time;
        private ImageView like;
        private ImageView comment;
        private RelativeLayout img_relative;
        private CardView cardView;
        private TextView numRepaly;
        private TextView numLike;

        public TopicsViewHolder(View itemView) {
            super(itemView);
            numLike = (TextView) itemView.findViewById(R.id.num_like);
            numRepaly = (TextView) itemView.findViewById(R.id.num_replay);
            cardView = (CardView) itemView.findViewById(R.id.item_view);
            img_relative = (RelativeLayout) itemView.findViewById(R.id.img_relative);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.user_content);
            userPhoto = (ImageView) itemView.findViewById(R.id.user_photo);
            photos = (ImageView) itemView.findViewById(R.id.first_image);
            time = (TextView) itemView.findViewById(R.id.article_time);
            like = (ImageView) itemView.findViewById(R.id.like);
            comment = (ImageView) itemView.findViewById(R.id.comment);
        }

    }

    private class PublishViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CardView publish;

        public PublishViewHolder(View itemView) {
            super(itemView);
            publish = (CardView) itemView.findViewById(R.id.publish);
            //可以试试Sp去缓存图片的地址
            image = (ImageView) itemView.findViewById(R.id.header_imageView);
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
        void onlikeClicked(TextView like,View view, int position);
        void onCommentClicke(int articleId);
        void onOthersPhotoClick();
    }

}
