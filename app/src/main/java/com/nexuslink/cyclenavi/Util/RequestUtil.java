package com.nexuslink.cyclenavi.Util;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;
import com.nexuslink.cyclenavi.Model.JavaBean.LikeBean;

import retrofit2.Call;

/**
 * Created by Rye on 2017/4/16.
 */

public class RequestUtil {
    private static ICycleNaviService service;
    static {
        service = RetrofitWrapper.getInstance().create(ICycleNaviService.class);
    }
    public static Call<FreshBean> more(int userId, String lastArticleId){
        return service.more(userId, lastArticleId);
    }

    public static Call<FreshBean> fresh(int userId) {
        return service.fresh(userId);
    }
    public static io.reactivex.Observable<LikeBean> like(int userId, int articleId) {
        return service.like(10,56);
    }
}
