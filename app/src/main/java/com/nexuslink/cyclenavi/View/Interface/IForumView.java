package com.nexuslink.cyclenavi.View.Interface;

import android.content.Context;

import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;

import java.util.List;

/**
 * Created by Rye on 2017/4/12.
 */

public interface IForumView {
    void showArticlesInRecycle(List<FreshBean.ArticlesBean> articles);

    void showMoreArticlesInRecycle(List<FreshBean.ArticlesBean> articles);

    void showError(String message);

    Context getThis();
}
