package com.nexuslink.cyclenavi.Presenter.Impl;

import com.nexuslink.cyclenavi.Model.Impl.CommentModel;
import com.nexuslink.cyclenavi.Model.Interface.ICommentModel;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;
import com.nexuslink.cyclenavi.View.Interface.ICommentView;

/**
 * Created by Rye on 2017/4/17.
 */

public class CommentPresenterImpl implements ICommentPresenter {
    private ICommentView commentView;
    private ICommentModel commentModel;
    public CommentPresenterImpl(ICommentView commentView) {
        this.commentView =  commentView;
        commentModel = new CommentModel(this);
    }

    //获取评论列表
    @Override
    public void obtainCommentList(int articleId) {
        // TODO: 2017/5/6 修改为参数
        commentModel.requestCommentList(56);
    }

    //添加新评论
    @Override
    public void addComment(String messageSend, int userId, String articleId) {
        commentModel.requestNewComment(10,56,"test");
    }

    //添加更多评论
    @Override
    public void obtainMoreComment(int articleId, int lastCommentFloor) {
        // TODO: 2017/5/6 修改为参数
        commentModel.requestMoreComment(56,40);
    }
}
