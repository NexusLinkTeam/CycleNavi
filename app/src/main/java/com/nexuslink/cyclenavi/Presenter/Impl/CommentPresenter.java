package com.nexuslink.cyclenavi.Presenter.Impl;

import com.nexuslink.cyclenavi.Model.Impl.CommentModel;
import com.nexuslink.cyclenavi.Model.Interface.ICommentModel;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;
import com.nexuslink.cyclenavi.View.Impl.Activities.CommentActivity;
import com.nexuslink.cyclenavi.View.Interface.ICommentView;

/**
 * Created by Rye on 2017/4/17.
 */

public class CommentPresenter implements ICommentPresenter {
    private ICommentView commentView;
    private ICommentModel commentModel;
    public CommentPresenter(ICommentView commentView) {
        this.commentView =  commentView;
        commentModel = new CommentModel(this);
    }

    @Override
    public void obtainCommentList() {
        commentModel.requestCommentList();
    }

    @Override
    public void addComment(String messageSend, String userId, String articleId) {
        commentModel.requestNewComment();
    }

    @Override
    public void obtainMoreComment() {
        commentModel.requestMoreComment();
    }
}
