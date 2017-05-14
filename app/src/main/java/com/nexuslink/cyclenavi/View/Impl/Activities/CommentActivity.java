package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Adapters.CommentAdapter;
import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;
import com.nexuslink.cyclenavi.Presenter.Impl.CommentPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.Util.ToastUtil;
import com.nexuslink.cyclenavi.View.Interface.ICommentView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener,ICommentView {
    private static final String ARTICLE_ID = "articleId";
    private static final int ARTICLE_ID_ERROR = -1;
    List<GetCommentsBean.CommentsBean> commentBeanList = new ArrayList<>();
    private TextView emptyInfo;
    private RecyclerView commentRecycle;
    private ICommentPresenter presenter;
    private ImageView sendComment;
    private EditText messageEdit;
    private int lastVisible;
    private LinearLayoutManager linearLayoutManager;
    private CommentAdapter commentAdapter;

    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        articleId = getIntent().getIntExtra(ARTICLE_ID,ARTICLE_ID_ERROR);

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
                Log.d("TAGMY","count"+commentAdapter.getItemCount() + "last" +
                        lastVisible);
                if(commentAdapter.getItemCount() > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisible + 1 == commentAdapter.getItemCount()){
                    if(articleId!=ARTICLE_ID_ERROR)
                    presenter.obtainMoreComment(articleId,lastVisible);//测试
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
    }

    private void initView() {
        getSupportActionBar().setTitle("评论列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messageEdit = (EditText) findViewById(R.id.message_edit);
        sendComment = (ImageView) findViewById(R.id.send_comment);
/*
        emptyInfo = (TextView) findViewById(R.id.emptyInfo);
*/
        commentRecycle = (RecyclerView) findViewById(R.id.recycle_comment);
        commentRecycle.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        presenter = new CommentPresenterImpl(this);
        if(articleId!=ARTICLE_ID_ERROR)
        presenter.obtainCommentList(articleId);
    }

    @Override
    public void onClick(View v) {
        String messageSend = messageEdit.getText().toString();
        int userId = SpUtil.getUserId();
        if(articleId!=ARTICLE_ID_ERROR)
        presenter.addComment(messageSend,userId,articleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadComments(List<GetCommentsBean.CommentsBean> comments) {
        commentAdapter.addComments(comments);
    }

    @Override
    public void update() {
        ToastUtil.shortToast("评论成功");
        messageEdit.setText("");
    }
}
