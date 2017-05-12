package com.nexuslink.cyclenavi.Model.Interface;

/**
 * Created by Rye on 2017/4/15.
 */

public interface IForumModel {
    void requestArticles();

    void requestMoreArticles(int lastArticleId);

    void like(int userId, int articleId);
}
