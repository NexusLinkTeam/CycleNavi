package com.nexuslink.cyclenavi.Presenter.Interface;

/**
 * Created by Rye on 2017/4/17.
 */

public interface ICommentPresenter {
    void obtainCommentList();

    void addComment(String messageSend, String userId, String articleId);

    void obtainMoreComment();
}
