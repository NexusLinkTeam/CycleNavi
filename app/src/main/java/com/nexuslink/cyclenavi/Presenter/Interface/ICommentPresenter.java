package com.nexuslink.cyclenavi.Presenter.Interface;

/**
 * Created by Rye on 2017/4/17.
 */

public interface ICommentPresenter {
    void obtainCommentList(int articleId);

    void addComment(String messageSend, int userId, String articleId);

    void obtainMoreComment(int articleId, int lastCommentFloor);
}
