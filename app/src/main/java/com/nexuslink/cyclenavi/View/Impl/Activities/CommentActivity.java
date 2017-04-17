package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Adapters.CommentAdapter;
import com.nexuslink.cyclenavi.Model.JavaBean.CommentBean;
import com.nexuslink.cyclenavi.Presenter.Impl.CommentPresenter;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.View.Interface.ICommentView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener,ICommentView {
    List<CommentBean> commentBeanList;
    private TextView emptyInfo;
    private RecyclerView commentRecycle;
    private ICommentPresenter presenter;
    private ImageView sendComment;
    private EditText messageEdit;
    private int lastVisible;
    private LinearLayoutManager linearLayoutManager;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        sendComment.setOnClickListener(this);
        commentRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(commentAdapter.getItemCount() > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisible == commentAdapter.getItemCount()){
                    presenter.obtainMoreComment();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    private void initData() {
        commentBeanList = new ArrayList<>();
        commentRecycle.setAdapter(commentAdapter = new CommentAdapter(this,commentBeanList));
        if(commentAdapter.getItemCount() > 1){
            emptyInfo.setVisibility(View.GONE);
        }else {
            emptyInfo.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        getSupportActionBar().setTitle("评论列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messageEdit = (EditText) findViewById(R.id.message_edit);
        sendComment = (ImageView) findViewById(R.id.send_comment);
        emptyInfo = (TextView) findViewById(R.id.emptyInfo);
        commentRecycle = (RecyclerView) findViewById(R.id.recycle_comment);
        commentRecycle.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        presenter = new CommentPresenter(this);
        presenter.obtainCommentList();
    }

    @Override
    public void onClick(View v) {
        String messageSend = messageEdit.getText().toString();
        String userId = SpUtil.getUserId(this);
        // TODO: 2017/4/17 Sp保存在点击Item时文章id
        String articleId = "";
        presenter.addComment(messageSend,userId,articleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
