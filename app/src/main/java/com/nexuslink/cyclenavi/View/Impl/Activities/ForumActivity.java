package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexuslink.cyclenavi.Adapters.RecycleTopicsAdapter;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Presenter.Impl.ForumPresenter;
import com.nexuslink.cyclenavi.Presenter.Interface.IForumPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.SpUtil;
import com.nexuslink.cyclenavi.View.Interface.IForumView;

import java.util.List;


public class ForumActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,IForumView, RecycleTopicsAdapter.onTopicClickListener, View.OnClickListener {
    private RecycleTopicsAdapter recyclerTopicsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerTopics;
    private FloatingActionButton fab;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private IForumPresenter presenter;
    private Boolean freshing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        presenter = new ForumPresenter(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerTopics.setLayoutManager(linearLayoutManager);
    }

    private void initEvent() {
        fab.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerTopics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerTopicsAdapter != null) {
                    if (recyclerTopicsAdapter.getArticles().size() > 0
                            && newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem == recyclerTopicsAdapter.getItemCount() - 1
                            && !freshing) {
                        int lastArticleId = recyclerTopicsAdapter.getArticles().
                                get(recyclerTopicsAdapter.getItemCount() - 3).getArticleId();
                        freshing = true;
                        presenter.obtainMoreArticles(lastArticleId);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.forum);
        actionBar.setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerTopics = (RecyclerView) findViewById(R.id.recycle_topics);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiftFresh);
        swipeRefreshLayout.setRefreshing(true);
        presenter.obtainArticles();
    }

    @Override
    public void onRefresh() {
        recyclerTopicsAdapter.removeAll();
        presenter.obtainArticles();
    }

    @Override
    public void showArticlesInRecycle(List<FreshBean.ArticlesBean> articles) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerTopicsAdapter = new RecycleTopicsAdapter(this,articles);
        recyclerTopicsAdapter.setOnTopicClickListener(this);
        recyclerTopics.setAdapter(recyclerTopicsAdapter);
    }

    @Override
    public void showMoreArticlesInRecycle(List<FreshBean.ArticlesBean> articles) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerTopicsAdapter.loadMore(articles);
        freshing = false;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        freshing = false;
    }

    @Override
    public Context getThis() {
        return this;
    }

    @Override
    public void successToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failToast(Throwable throwable) {
        Toast.makeText(this,throwable.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onlikeClicked(TextView like, View view, int position) {
        // TODO: 2017/4/17 点赞的request
        ImageView imageView = (ImageView) view;
        int count = Integer.parseInt(like.getText().toString());
        FreshBean.ArticlesBean current = recyclerTopicsAdapter.getArticles().get(position - 1);
        Boolean islike = recyclerTopicsAdapter.getArticles().get(position - 1).isLikeArticle();
        if(!islike){
            count ++;
            like.setText(String.valueOf(count));
            imageView.setImageDrawable(getDrawable(R.drawable.like));
            current.setLikeArticle(true);
        }else {
            count--;
            like.setText(String.valueOf(count));
            imageView.setImageDrawable(getDrawable(R.drawable.unlike));
            current.setLikeArticle(false);
        }
        int articleId = recyclerTopicsAdapter.getArticles().get(position - 1).getArticleId();
        presenter.likeThis(SpUtil.getUserId(),articleId);//赞与取消赞是同一个接口
    }

    @Override
    public void onCommentClicke(int articleId) {
        Intent intent = new Intent(ForumActivity.this,CommentActivity.class);
        intent.putExtra("articleId",articleId);
        startActivity(intent);
    }

    @Override
    public void onOthersPhotoClick() {
        Intent intent = new Intent(ForumActivity.this,PersonalActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(this,PublishDialogActivity.class);
                startActivity(intent);
                break;
        }
    }
}
