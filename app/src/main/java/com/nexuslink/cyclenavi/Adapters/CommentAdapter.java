package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;
import com.nexuslink.cyclenavi.R;

import java.util.List;

/**
 * Created by Rye on 2017/4/12.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPLE_LOAD = 1;
    private static final int TYPLE_NORMAL = 0;
    private static final String TAG = "MY_TAG";
    private Context context;
    private List<GetCommentsBean.CommentsBean> commentBeanList;
    public CommentAdapter(Context context, List<GetCommentsBean.CommentsBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == getItemCount() - 1){
            return TYPLE_LOAD;
        }else {
            return TYPLE_NORMAL;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPLE_LOAD){
            return new LoadViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bottom_load,parent,false));
        }else if(viewType == TYPLE_NORMAL){
            return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_replay,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommentViewHolder){
            ((CommentViewHolder) holder).replay.setText(commentBeanList.get(position).getContent());
            ((CommentViewHolder) holder).name.setText(commentBeanList.get(position).getUser().getUserName());
            Log.d(TAG, "onBindViewHolder: " + commentBeanList.get(position).getUser().getUserImg());
            Glide.with(context).load("http://120.77.87.78:8080/cycle/image/" + commentBeanList.get(position).getUser().getUserImg()).into(((CommentViewHolder) holder).imageUser);
        }
    }

    @Override
    public int getItemCount() {
        return commentBeanList.size() + 1;
    }

    public void addComments(List<GetCommentsBean.CommentsBean> comments) {
        commentBeanList.addAll(comments);
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageUser;
        private TextView name;
        private TextView replay;

        public CommentViewHolder(View itemView) {
            super(itemView);
            imageUser = (ImageView) itemView.findViewById(R.id.img_user);
            name = (TextView) itemView.findViewById(R.id.text_name);
            replay = (TextView) itemView.findViewById(R.id.text_replay);
        }
    }

    private class LoadViewHolder extends RecyclerView.ViewHolder {
        public LoadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
