package com.nexuslink.cyclenavi.Extra.Net;

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
import com.nexuslink.cyclenavi.Model.JavaBean.PublishBean;
import com.nexuslink.cyclenavi.Model.JavaBean.RegisterBean;
import com.nexuslink.cyclenavi.Model.JavaBean.SaveNameBean;
import com.nexuslink.cyclenavi.Model.JavaBean.SaveRouteBean;
import com.nexuslink.cyclenavi.Model.JavaBean.UpLoadBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 把所有接口更换为RxJava,在项目完成后才使用这个
 * Created by Rye on 2017/5/5.
 */

public interface ApiService {

    //注册接口
    @FormUrlEncoded
    @POST("/cycle/api/user/register")
    Observable<RegisterBean> register(@Field("userName")String userName,
                                      @Field("userPassword")String userPassWord,
                                      @Field("userEmergencyPhone")String userEmergencyPhone);
    //登录接口
    @FormUrlEncoded
    @POST("/cycle/api/user/login")
    Observable<LoginBean> login(@Field("userName")String userName,
                          @Field("userPassword")String userPassWord);

    //更换用户头像
    @Multipart
    @POST("/cycle/api/img/changeUser")
    Observable<UpLoadBean> upLoad(@Part("uid") RequestBody uid,
                            @Part MultipartBody.Part userImg);
    //？？？
    @FormUrlEncoded
    @POST("/cycle/api/user/nameSaved")
    Observable<SaveNameBean> saveName(@Field("userName") String userName);

    //发布文字说说
    @FormUrlEncoded
    @POST("/cycle/api/article/publish")
    Observable<PublishBean> publish(@Field("userId") String userId,
                              @Field("content") String content);

    //发布图片
    @Multipart
    @POST("/cycle/api/img/article")
    Observable<ArticleBean> article(@Part("articleId") RequestBody articleId,
                              @Part("userId") RequestBody userId,
                              @Part List<MultipartBody.Part> articleImgs);
    // 获得前10 篇文章
    @FormUrlEncoded
    @POST("/cycle/api/article/getArticle")
    Observable<GetArticleBean> getArticle(@Field("userId") String userId,
                                    @Field("articleId") String articleId );
    //刷新文章
    @FormUrlEncoded
    @POST("/cycle/api/article/fresh")
    Observable<FreshBean> fresh(@Field("userId") String userId );

    //加载更多文章
    @FormUrlEncoded
    @POST("/cycle/api/article/more")
    Observable<FreshBean> more(@Field("userId")String userId, @Field("lastArticleId") String lastArticleId);

    //获得他的前10篇说说
    @FormUrlEncoded
    @POST("/cycle/api/article/getHis")
    Observable<HitsBean> getHis(@Field("userId") String userId,
                          @Field("writerId") String writerId);
    //获得他的更多说说
    @FormUrlEncoded
    @POST("/cycle/api/article/getHisMore")
    Observable<GetMoreHitsBean> getHisMore(@Field("userId") String userId,
                                     @Field("writerId") String writerId,
                                     @Field("lastArticleId") String lastArticleId );

    //=========================================以上未完成测试=======================================
    //上传骑行数据（测试完成）
    @Multipart
    @POST("/cycle/api/user/saveRoute")
    Observable<SaveRouteBean> saveRout(@Part("userId") RequestBody userId,
                                            @Part("totalTime") RequestBody totalTime,
                                            @Part("date") RequestBody date,
                                            @Part("routeLine") RequestBody routeLine,
                                            @Part("speedList") RequestBody speedList,
                                            @Part("heightList") RequestBody heightList,
                                            @Part MultipartBody.Part picture);
    //对特定文章进行评论(完成测试)
    @FormUrlEncoded
    @POST("/cycle/api/article/comment")
    Observable<CommentBean> comment(@Field("userId") int userId,
                                    @Field("articleId") int articleId,
                                    @Field("content") String content );

    //获得特定文章的前10个评论（测试完成）
    @FormUrlEncoded
    @POST("/cycle/api/article/getComments")
    Observable<GetCommentsBean> getComments (@Field("articleId") int articleId );

    //获得特定文章的更多评论（测试成功）
    @FormUrlEncoded
    @POST("/cycle/api/article/getMoreComments")
    Observable<GetMoreComments> getMoreComments (@Field("articleId") int userId,
                                                 @Field("lastCommentFloor") int lastCommentFloor);

    //点赞(测试成功)
    @FormUrlEncoded
    @POST("/cycle/api/article/like")
    Observable<LikeBean> like(@Field("userId") int userId,
                              @Field("articleId") int articleId );
}
