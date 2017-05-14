package com.nexuslink.cyclenavi.Presenter.Impl;

import android.util.Log;

import com.nexuslink.cyclenavi.Model.Impl.CommentModel;
import com.nexuslink.cyclenavi.Model.Interface.ICommentModel;
import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;
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
        commentModel.requestCommentList(articleId);
    }

    //添加新评论
    @Override
    public void addComment(String messageSend, int userId, int articleId) {
        commentModel.requestNewComment(userId,articleId,messageSend);
    }

    //添加更多评论
    @Override
    public void obtainMoreComment(int articleId, int lastCommentFloor) {
        // TODO: 2017/5/6 修改为参数
        commentModel.requestMoreComment(articleId,lastCommentFloor);
    }

    @Override
    public void responseSuccess(GetCommentsBean getCommentsBean) {
        commentView.loadComments(getCommentsBean.getComments());
    }

    @Override
    public void newResponse() {
        commentView.update();
    }

    @Override
    public void moreResponse(GetCommentsBean getCommentsBean) {
        Log.d("MY_TAG", "moreResponse: "+"返回值");
        commentView.loadComments(getCommentsBean.getComments());
    }
}
