package com.nexuslink.cyclenavi.Presenter.Interface;

import android.content.Context;

import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;

import java.util.List;

/**
 * Created by Rye on 2017/4/15.
 */

public interface IForumPresenter {
    void obtainArticles();

    void reciveArticles(List<FreshBean.ArticlesBean> articles);

    void obtainMoreArticles(int lastArticleId);

    void reciveMoreArticles(List<FreshBean.ArticlesBean> articles);

    void requestFailed(String message);

    Context getContext();
}
