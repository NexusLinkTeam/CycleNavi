package com.nexuslink.cyclenavi.Presenter.Impl;

import android.content.Context;

import com.nexuslink.cyclenavi.Model.Impl.ForumModel;
import com.nexuslink.cyclenavi.Model.Interface.IForumModel;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Presenter.Interface.IForumPresenter;
import com.nexuslink.cyclenavi.View.Interface.IForumView;

import java.util.List;

/**
 * Created by Rye on 2017/4/15.
 */

public class ForumPresenter implements IForumPresenter {
    private IForumView forumView;
    private IForumModel forumModel;
    public ForumPresenter(IForumView forumView) {
        this.forumView = forumView;
        forumModel = new ForumModel(this);
    }

    @Override
    public void obtainArticles() {
        forumModel.requestArticles();
    }

    @Override
    public void reciveArticles(List<FreshBean.ArticlesBean> articles) {
        forumView.showArticlesInRecycle(articles);
    }

    @Override
    public void obtainMoreArticles(int lastArticleId) {
        forumModel.requestMoreArticles(lastArticleId);
    }

    @Override
    public void reciveMoreArticles(List<FreshBean.ArticlesBean> articles) {
        forumView.showMoreArticlesInRecycle(articles);
    }

    @Override
    public void requestFailed(String message) {
        forumView.showError(message);
    }

    @Override
    public Context getContext() {
        return forumView.getThis();
    }

    @Override
    public boolean checkIsLike() {
        return false;
    }

    @Override
    public void likeSuccess() {
    }

    @Override
    public void likeFail(Throwable throwable) {
        forumView.failToast(throwable);
    }

    @Override
    public void likeThis(int userId, int articleId) {
        forumModel.like(userId, articleId);
    }
}
