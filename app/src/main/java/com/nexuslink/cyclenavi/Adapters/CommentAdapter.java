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

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPLE_LOAD = 1;
    private static final int TYPLE_NORMAL = 0;
    private Context context;
    public CommentAdapter(Context context, List<CommentBean> commentBeanList) {
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class LoadViewHolder extends RecyclerView.ViewHolder {
        public LoadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
