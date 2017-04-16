package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nexuslink.cyclenavi.Adapters.CommentAdapter;
import com.nexuslink.cyclenavi.Model.JavaBean.CommentBean;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    List<CommentBean> commentBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentBeanList = new ArrayList<>();

        RecyclerView commentRecycle = (RecyclerView) findViewById(R.id.recycle_comment);
        commentRecycle.setLayoutManager(new LinearLayoutManager(this));
        commentRecycle.setAdapter(new CommentAdapter(this,commentBeanList));
    }
}
