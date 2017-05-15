package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexuslink.cyclenavi.Adapters.PersonTopicRecyclerAdapter;
import com.nexuslink.cyclenavi.Adapters.RecycleTopicsAdapter;
import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Model.JavaBean.GetHisMore;
import com.nexuslink.cyclenavi.Model.JavaBean.GetMoreHitsBean;
import com.nexuslink.cyclenavi.Model.JavaBean.HitsBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.Util.SpUtils;
import com.nexuslink.cyclenavi.View.Impl.Activities.CommentActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.ForumActivity;
import com.nexuslink.cyclenavi.View.Impl.Activities.PersonalActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by G150S on 2017/4/29.
 */

public class PersonInfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private final String TAG = "PersonInfoFragment";
    private SwipeRefreshLayout personSwipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private PersonTopicRecyclerAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String mTitle;
    private List<HitsBean.ArticlesBean> articles;
    private int lastVisibleItem;
    private boolean isUpLoadingPersonInfo = false;

    private static final String ARG_TITLE = "title";
    private static final String CONTEXT = "context";

    public static PersonInfoFragment getInstance(String title) {
        PersonInfoFragment fra = new PersonInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_fragment, container, false);

        initData();
        personSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.person_swiftFresh);
        personSwipeRefreshLayout.setColorSchemeResources(R.color.RED,R.color.Yellow
                ,R.color.colorPrimaryDark,R.color.colorAccent2,R.color.colorAccent3);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.person_recycle_topics);
        mRecyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mAdapter = new PersonTopicRecyclerAdapter(mRecyclerView.getContext(), articles));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mAdapter.getArticles().size() > 0 && newState == RecyclerView.SCROLL_STATE_IDLE//RecyclerView当前不在滑动
                        && lastVisibleItem == mAdapter.getItemCount() - 1){
                    getMoreArticles(mAdapter.getArticles().get(articles.size() - 1).getArticleId());
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个能见的位置
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
        personSwipeRefreshLayout.setRefreshing(true);
        isUpLoadingPersonInfo = true;
        getPersonArticles();
        personSwipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }

    //初始化数据
    protected void initData() {
        articles = new ArrayList<>();
    }

    //得到用户自己发布的10个话题
    private void getPersonArticles()
    {
        Log.e(TAG,SpUtils.getInt(getContext(),"uid")+"");
        RetrofitWrapper.getInstance().create(ICycleNaviService.class)
                .getOnesArticle(SpUtils.getInt(getContext(),"uid"),SpUtils.getInt(getContext(),"uid"))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HitsBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"下拉刷新完成");
                        isUpLoadingPersonInfo = false;
                    }
                    @Override
                    public void onError(Throwable e) {
                        personSwipeRefreshLayout.setRefreshing(false);
                        isUpLoadingPersonInfo = false;
                        Toast.makeText(getContext(),"下拉刷新请求失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(HitsBean hitsBean) {
                        if(hitsBean.getCode() == 200){
                            Log.e(TAG,hitsBean.getCode()+"下拉刷新请求数据成功");
                            mAdapter.removeAll();
                            //请求成功，关闭刷新
                            personSwipeRefreshLayout.setRefreshing(false);
                            Log.e(TAG,hitsBean.getArticles().size()+"下拉刷新的话题数量");
                            mAdapter.loadMore(hitsBean.getArticles());
                            mAdapter.setOnTopicClickListener(new RecycleTopicsAdapter.onTopicClickListener() {
                                @Override
                                public void onlikeClicked(View view) {
                                    ImageView imageView = (ImageView) view;
                                    imageView.setImageDrawable(getContext().getDrawable(R.drawable.like));
                                }
                                @Override
                                public void onCommentClicke() {
                                    Intent intent = new Intent(getContext(),CommentActivity.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onOthersPhotoClick() {
                                    //区分
                                    Intent intent = new Intent(getContext(),PersonalActivity.class);
                                    startActivity(intent);
                                }
                            });
                            mRecyclerView.setAdapter(mAdapter);
                        }else {
                            Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //继续得到用户自己发布的10个话题
    private void getMoreArticles(final int last)
    {
        RetrofitWrapper.getInstance().create(ICycleNaviService.class)
                .getOnesMore(SpUtils.getInt(getContext(),"uid"),SpUtils.getInt(getContext(),"uid"),last)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HitsBean>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(HitsBean hitsBean) {
                        if(hitsBean.getCode() == 200){
                            if(hitsBean.getArticles().size() == 0){
                                //到底了

                            }else {
                                mAdapter.loadMore(hitsBean.getArticles());
                            }
                        }else {
                            Toast.makeText(getContext(),"请求失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //SwipeRefreshLayout刷新回调
    @Override
    public void onRefresh() {
        if (!isUpLoadingPersonInfo) {
            Log.e(TAG, "onRefresh");
            getPersonArticles();
        }
    }
}
