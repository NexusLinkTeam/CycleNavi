package com.nexuslink.cyclenavi.Model.JavaBean;

/**
 * Created by Rye on 2017/3/3.
 */
public class LikeBean {

    /**
     * likeArticle : true
     * code : 200
     */

    private boolean likeArticle;
    private int code;

    public boolean isLikeArticle() {
        return likeArticle;
    }

    public void setLikeArticle(boolean likeArticle) {
        this.likeArticle = likeArticle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
