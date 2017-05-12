package com.nexuslink.cyclenavi.Model.Interface;

/**
 * Created by Rye on 2017/4/17.
 */

public interface ICommentModel {
    void requestCommentList(int articleId);

    void requestNewComment(int userId,int articleId, String content);

    void requestMoreComment(int userId,int lastCommentFloor);
}
