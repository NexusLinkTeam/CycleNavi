package com.nexuslink.cyclenavi.Model.Impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nexuslink.cyclenavi.Extra.Net.ApiService;
import com.nexuslink.cyclenavi.Extra.Net.RetrofitClient;
import com.nexuslink.cyclenavi.Model.Interface.IForumModel;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Model.JavaBean.LikeBean;
import com.nexuslink.cyclenavi.Presenter.Interface.IForumPresenter;
import com.nexuslink.cyclenavi.Util.RequestUtil;
import com.nexuslink.cyclenavi.Util.SpUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rye on 2017/4/15.
 */

public class ForumModel implements IForumModel {
    private IForumPresenter presenter;
    public ForumModel(IForumPresenter presenter) {
        this.presenter = presenter;
    }


    //请求文章
    @Override
    public void requestArticles() {
        //直接通过MyApplication的静态方法取值
        int userId = SpUtil.getUserId();
        RequestUtil.fresh(userId).enqueue(new Callback<FreshBean>() {

            @Override
            public void onResponse(Call<FreshBean> call, Response<FreshBean> response) {
                if(response.body() != null && response.body().getCode() == 200){
                    List<FreshBean.ArticlesBean> articles = response.body().getArticles();
                    presenter.reciveArticles(articles);
                }
            }

            @Override
            public void onFailure(Call<FreshBean> call, Throwable t) {

            }
        });
    }

    //请求更多文章
    @Override
    public void requestMoreArticles(int lastArticleId) {
        //直接通过MyApplication的静态方法取值
        int userId = SpUtil.getUserId();
        Log.d("MY_TAG","Model层收到即将请求信息");
        RequestUtil.more(userId,
                String.valueOf(lastArticleId)).enqueue(new Callback<FreshBean>() {

            @Override
            public void onResponse(Call<FreshBean> call, Response<FreshBean> response) {
                Log.d("MY_TAG","服务器返回值"+ response.body().getCode());
                if(response.body().getCode() == 200){
                    //追加的说说
                    List<FreshBean.ArticlesBean> articles = response.body().getArticles();
                    Log.d("MY_TAG","获得说说"+articles.size()+"条");
                    presenter.reciveMoreArticles(articles);
                }else {

                }
            }

            @Override
            public void onFailure(Call<FreshBean> call, Throwable t) {
                presenter.requestFailed(t.getMessage());
            }
        });
    }

    //文章点赞
    @Override
    public void like(int userId, int articleId) {
        RetrofitClient.create (ApiService.class)
                .like(userId,articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LikeBean>() {
                    @Override
                    public void accept(@NonNull LikeBean likeBean) throws Exception {
                        presenter.likeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        presenter.likeFail(throwable);
                    }
                });
    }
}
