package com.nexuslink.cyclenavi.Model.Impl;

import android.util.Log;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.Interface.IForumModel;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Presenter.Interface.IForumPresenter;
import com.nexuslink.cyclenavi.Util.RequestUtil;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;

import java.util.List;

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

    @Override
    public void requestArticles() {
        int userId = getUserIdFromSp();
        RequestUtil.fresh(String.valueOf(userId)).enqueue(new Callback<FreshBean>() {

            @Override
            public void onResponse(Call<FreshBean> call, Response<FreshBean> response) {
                if(response.body().getCode() == 200){
                    List<FreshBean.ArticlesBean> articles = response.body().getArticles();
                    presenter.reciveArticles(articles);
                }
            }

            @Override
            public void onFailure(Call<FreshBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void requestMoreArticles(int lastArticleId) {
        int userId = getUserIdFromSp();
        Log.d("MY_TAG","Model层收到即将请求信息");
        RequestUtil.more(String.valueOf(userId),
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

    private int getUserIdFromSp() {
        //测试
        return 10;
    }
}
