package com.nexuslink.cyclenavi.Presenter.Interface;

import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;

/**
 * Created by Rye on 2017/4/17.
 */

public interface ICommentPresenter {
    void obtainCommentList(int articleId);

    void addComment(String messageSend, int userId, int articleId);

    void obtainMoreComment(int articleId, int lastCommentFloor);

    void responseSuccess(GetCommentsBean getCommentsBean);

    void newResponse();

    void moreResponse(GetCommentsBean getCommentsBean);
}
