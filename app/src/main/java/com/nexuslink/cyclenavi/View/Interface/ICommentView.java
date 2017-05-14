package com.nexuslink.cyclenavi.View.Interface;

import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;

import java.util.List;

/**
 * Created by Rye on 2017/4/17.
 */

public interface ICommentView {
    void loadComments(List<GetCommentsBean.CommentsBean> comments);

    void update();
}
