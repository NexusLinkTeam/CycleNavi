package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexuslink.cyclenavi.Adapters.RecycleTopicsAdapter;
import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.FreshEvent;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForumActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecycleTopicsAdapter recyclerTopicsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerTopics;
    private List<FreshBean.ArticlesBean> articles;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        EventBus.getDefault().register(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.forum);
        actionBar.setDisplayHomeAsUpEnabled(true);

        articles = new ArrayList<>();

        recyclerTopics = (RecyclerView) findViewById(R.id.recycle_topics);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiftFresh);

        recyclerTopics.setLayoutManager(linearLayoutManager = new LinearLayoutManager(ForumActivity.this));

        recyclerTopicsAdapter = new RecycleTopicsAdapter(ForumActivity.this,articles);

        recyclerTopics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerTopicsAdapter.getArticles().size() > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == recyclerTopicsAdapter.getItemCount() - 1){
                    showNextPage(recyclerTopicsAdapter.getArticles().get(articles.size() - 1).getArticleId());
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个能见的位置
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });

        //创建时加载数据
        swipeRefreshLayout.setRefreshing(true);
        getArticles(recyclerTopics);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void showNextPage(final int last) {
        RetrofitWrapper.getInstance().create(ICycleNaviService.class).more("10",last+"").enqueue(new Callback<FreshBean>() {
            @Override
            public void onResponse(Call<FreshBean> call, Response<FreshBean> response) {
                if(response.code() == 200){
                    if(response.body().getArticles().size() == 0){
                       //到底了
                        
                    }else {
                        recyclerTopicsAdapter.loadMore(response.body().getArticles());
                    }
                }else {
                    Toast.makeText(ForumActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FreshBean> call, Throwable t) {

            }
        });
    }
    private void getArticles(final RecyclerView recyclerTopics) {
        //获得UserID
        RetrofitWrapper.getInstance().create(ICycleNaviService.class).fresh("10").enqueue(new Callback<FreshBean>() {
            @Override
            public void onResponse(Call<FreshBean> call, final Response<FreshBean> response) {
                if(response.code() == 200){
                    //请求成功，关闭刷新
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerTopicsAdapter.loadMore(response.body().getArticles());
                    recyclerTopicsAdapter.setOnTopicClickListener(new RecycleTopicsAdapter.onTopicClickListener() {
                        @Override
                        public void onlikeClicked(View view) {
                            ImageView imageView = (ImageView) view;
                            imageView.setImageDrawable(getDrawable(R.drawable.like));
                        }

                        @Override
                        public void onCommentClicke() {
                            Intent intent = new Intent(ForumActivity.this,CommentActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onOthersPhotoClick() {
                            //区分
                            Intent intent = new Intent(ForumActivity.this,PersonalActivity.class);
                            startActivity(intent);
                        }
                    });
                    recyclerTopics.setAdapter(recyclerTopicsAdapter);
                }else {
                    Toast.makeText(ForumActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FreshBean> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onFreshEvent(FreshEvent messageEvent){
        getArticles(recyclerTopics);
    }

    @Override
    public void onRefresh() {
        recyclerTopicsAdapter.removeAll();
        getArticles(recyclerTopics);
    }
}
