package com.nexuslink.cyclenavi.Model.Interface;

import com.nexuslink.cyclenavi.Model.Impl.ForumModel;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;

import java.util.List;

/**
 * Created by Rye on 2017/4/15.
 */

public interface IForumModel {
    void requestArticles();

    void requestMoreArticles(int lastArticleId);
}
