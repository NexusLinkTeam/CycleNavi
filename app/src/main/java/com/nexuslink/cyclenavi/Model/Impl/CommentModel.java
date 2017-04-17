package com.nexuslink.cyclenavi.Model.Impl;

import com.nexuslink.cyclenavi.Model.Interface.ICommentModel;
import com.nexuslink.cyclenavi.Presenter.Impl.CommentPresenter;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;
import com.nexuslink.cyclenavi.Util.RequestUtil;

/**
 * Created by Rye on 2017/4/17.
 */

public class CommentModel implements ICommentModel {
    private ICommentPresenter commentPresenter;
    public CommentModel(ICommentPresenter commentPresenter) {
        this.commentPresenter = commentPresenter;
    }

    @Override
    public void requestCommentList() {
        // TODO: 2017/4/17 请求评论列表
    }

    @Override
    public void requestNewComment() {
        // TODO: 2017/4/17  添加列表
    }

    @Override
    public void requestMoreComment() {

    }
}
