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
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Presenter.Impl.ForumPresenter;
import com.nexuslink.cyclenavi.Presenter.Interface.IForumPresenter;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Interface.IForumView;

import java.util.List;


public class ForumActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,IForumView, RecycleTopicsAdapter.onTopicClickListener {
    private RecycleTopicsAdapter recyclerTopicsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerTopics;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private IForumPresenter presenter;
    private Boolean freshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
       /* EventBus.getDefault().register(this);*/

        presenter = new ForumPresenter(this);

        initView();
        initData();
        initEvent();

       /* articles = new ArrayList<>();

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
    }*/
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
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerTopics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("MY_TAG","当前recycle中articleSize ："+""+ recyclerTopicsAdapter.getArticles().size());
                Log.d("MY_TAG","lastVisibleItem ："+""+ lastVisibleItem);
                Log.d("MY_TAG","recyclerTopicsAdapter.getItemCount() - 1 :"+ (recyclerTopicsAdapter.getItemCount() - 1));
                if(recyclerTopicsAdapter.getArticles().size() > 0
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == recyclerTopicsAdapter.getItemCount() - 1
                        && !freshing){
                    int lastArticleId = recyclerTopicsAdapter.getArticles().
                            get(recyclerTopicsAdapter.getItemCount() - 3).getArticleId();
                    Log.d("MY_TAG","条件通过");
                    Log.d("MY_TAG","是否在正在刷新"+String.valueOf(freshing));
                    Log.d("MY_TAG","最后一篇文章id"+String.valueOf(lastArticleId));
                    freshing = true;
                    presenter.obtainMoreArticles(lastArticleId);
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

        //初始化toolbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.forum);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
}
