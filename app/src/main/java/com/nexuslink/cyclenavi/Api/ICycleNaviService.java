package com.nexuslink.cyclenavi.Api;

import com.nexuslink.cyclenavi.Model.JavaBean.ArticleBean;
import com.nexuslink.cyclenavi.Model.JavaBean.CommentBean;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Model.JavaBean.GetArticleBean;
import com.nexuslink.cyclenavi.Model.JavaBean.GetCommentsBean;
import com.nexuslink.cyclenavi.Model.JavaBean.GetMoreComments;
import com.nexuslink.cyclenavi.Model.JavaBean.GetMoreHitsBean;
import com.nexuslink.cyclenavi.Model.JavaBean.HitsBean;
import com.nexuslink.cyclenavi.Model.JavaBean.LikeBean;
import com.nexuslink.cyclenavi.Model.JavaBean.LoginBean;
import com.nexuslink.cyclenavi.Model.JavaBean.MoreBean;
import com.nexuslink.cyclenavi.Model.JavaBean.PublishBean;
import com.nexuslink.cyclenavi.Model.JavaBean.RegisterBean;
import com.nexuslink.cyclenavi.Model.JavaBean.SaveNameBean;
import com.nexuslink.cyclenavi.Model.JavaBean.UpLoadBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 后台提供的接口
 * Created by Rye on 2017/1/21.
 */

public interface ICycleNaviService {
    @FormUrlEncoded
    @POST("/cycle/api/user/register")
    Call<RegisterBean> register(@Field("userName")String userName,
                                    @Field("userPassword")String userPassWord,
                                    @Field("userEmergencyPhone")String userEmergencyPhone);
    @FormUrlEncoded
    @POST("/cycle/api/user/login")
    Call<LoginBean> login(@Field("userName")String userName,
                          @Field("userPassword")String userPassWord);

    @Multipart
    @POST("/cycle/api/img/changeUser")
    Call<UpLoadBean> upLoad(@Part("uid")RequestBody uid,
                            @Part MultipartBody.Part userImg);
    @FormUrlEncoded
    @POST("/cycle/api/user/nameSaved")
    Call<SaveNameBean> saveName(@Field("userName") String userName);

    //论坛相关接口
    @FormUrlEncoded
    @POST("/cycle/api/article/publish")
    Call<PublishBean> publish(@Field("userId") String userId,
                              @Field("content") String content);

    @Multipart
    @POST("/cycle/api/img/article")
    Call<ArticleBean> article(@Part("articleId") RequestBody articleId,
                                @Part("userId") RequestBody userId,
                              @Part List<MultipartBody.Part> articleImgs);

    @FormUrlEncoded
    @POST("/cycle/api/article/getArticle")
    Call<GetArticleBean> getArticle(@Field("userId") String userId,
                                    @Field("articleId") String articleId );

    @FormUrlEncoded
    @POST("/cycle/api/article/fresh")
    Call<FreshBean> fresh(@Field("userId") String userId );

    @FormUrlEncoded
    @POST("/cycle/api/article/more")
    Call<FreshBean> more(@Field("userId")String userId, @Field("lastArticleId") String lastArticleId);

    @FormUrlEncoded
    @POST("/cycle/api/article/getHis")
    Call<HitsBean> getHis(@Field("userId") String userId,
                          @Field("writerId") String writerId);

    @FormUrlEncoded
    @POST("/cycle/api/article/getHisMore")
    Call<GetMoreHitsBean> getHisMore(@Field("userId") String userId,
                                     @Field("writerId") String writerId,
                                     @Field("lastArticleId") String lastArticleId );

    @FormUrlEncoded
    @POST("/cycle/api/article/like")
    Call<LikeBean> like(@Field("userId") String userId,
                        @Field("articleId") String articleId );

    @FormUrlEncoded
    @POST("/cycle/api/article/comment")
    Call<CommentBean> comment(@Field("userId") String userId,
                              @Field("articleId") String articleId,
                              @Field("content") String content );

    @FormUrlEncoded
    @POST("/cycle/api/article/getComments")
    Call<GetCommentsBean> getComments (@Field("articleId") String articleId );

    @FormUrlEncoded
    @POST("/cycle/api/article/getMoreComments")
    Call<GetMoreComments> getMoreComments (@Field("userId") String userId,
                                           @Field("lastCommentFloor") String lastCommentFloor  );
}
