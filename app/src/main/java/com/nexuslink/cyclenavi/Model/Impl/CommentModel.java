package com.nexuslink.cyclenavi.Model.Impl;

import android.util.Log;

import com.nexuslink.cyclenavi.Extra.Net.ApiService;
import com.nexuslink.cyclenavi.Extra.Net.RetrofitClient;
import com.nexuslink.cyclenavi.Model.Interface.ICommentModel;
import com.nexuslink.cyclenavi.Model.JavaBean.CommentBean;
import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;
import com.nexuslink.cyclenavi.Presenter.Interface.ICommentPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by Rye on 2017/4/17.
 */

public class CommentModel implements ICommentModel {
    private static final String TAG = "MY_TAG";
    private ICommentPresenter commentPresenter;
    public CommentModel(ICommentPresenter commentPresenter) {
        this.commentPresenter = commentPresenter;
    }

    //2017.5.8完成所有测试

    //请求评论列表（完成测试）
    @Override
    public void requestCommentList(int articleId) {
        //完成测试
        RetrofitClient.create(ApiService.class)
                .getComments(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCommentsBean>() {
                    @Override
                    public void accept(@NonNull GetCommentsBean getCommentsBean) throws Exception {
                        commentPresenter.responseSuccess(getCommentsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept:请求评论 " + throwable.getMessage());
                    }
                });

    }

    //添加新的评论（完成测试）
    @Override
    public void requestNewComment(int userId,int articleId, String content) {
        RetrofitClient.create(ApiService.class)
                .comment(userId,articleId,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentBean>() {
                    @Override
                    public void accept(@NonNull CommentBean commentBean) throws Exception {
                        Log.d(TAG, "accept: commentId" + commentBean.getCommentId());
                        commentPresenter.newResponse();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: commentId" + throwable.getMessage());
                    }
                });
    }

    //请求更多评论（完成测试）
    @Override
    public void requestMoreComment(int userId,int lastCommentFloor) {
        RetrofitClient.create(ApiService.class)
                .getMoreComments(userId, lastCommentFloor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCommentsBean>() {
                    @Override
                    public void accept(@NonNull GetCommentsBean getCommentsBean) throws Exception {
                        commentPresenter.moreResponse(getCommentsBean);
                    }

                   /* @Override
                    public void accept(@NonNull GetMoreComments getMoreComments) throws Exception {
                        commentPresenter.moreResponse(getMoreComments);
                        Log.d(TAG, "accept: commentId:更多" + getMoreComments.getComments().get(0).getCommentId());
                    }*/
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: commentId：获取更多失败" + throwable.getMessage());
                    }
                });
    }
}
