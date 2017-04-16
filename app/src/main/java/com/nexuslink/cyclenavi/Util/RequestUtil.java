package com.nexuslink.cyclenavi.Util;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.FreshBean;

import retrofit2.Call;

/**
 * Created by Rye on 2017/4/16.
 */

public class RequestUtil {
    private static ICycleNaviService service;
    static {
        service = RetrofitWrapper.getInstance().create(ICycleNaviService.class);
    }
    public static Call<FreshBean> more(String userId, String lastArticleId){
        return service.more(userId, lastArticleId);
    }

    public static Call<FreshBean> fresh(String userId) {
        return service.fresh(userId);
    }
}
