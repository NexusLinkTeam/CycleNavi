package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.cyclenavi.Model.JavaBean.CommentBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Impl.Activities.CommentActivity;

import java.util.List;

/**
 * Created by Rye on 2017/4/12.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    public CommentAdapter(Context context, List<CommentBean> commentBeanList) {
        this.context = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_replay,parent,false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
