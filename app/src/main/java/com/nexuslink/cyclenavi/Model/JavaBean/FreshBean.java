package com.nexuslink.cyclenavi.Model.JavaBean;

import java.util.List;

/**
 * Created by Rye on 2017/3/3.
 */
public class FreshBean {

    /**
     * code : 200
     * articles : [{"articleId":7,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user10.png"},"articleContent":"123","articleImgs":[],"lookNum":1,"likeNum":0,"commentNum":0,"date":"2017-03-03","time":"10:07:48","likeArticle":false},{"articleId":6,"user":{"userId":5,"userName":"233","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user5.png"},"articleContent":"第6","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-02-20","time":"22:31:42","likeArticle":false},{"articleId":5,"user":{"userId":5,"userName":"233","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user5.png"},"articleContent":"第5","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-02-20","time":"22:31:37","likeArticle":false},{"articleId":4,"user":{"userId":5,"userName":"233","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user5.png"},"articleContent":"第4","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-02-20","time":"22:31:31","likeArticle":false},{"articleId":3,"user":{"userId":5,"userName":"233","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user5.png"},"articleContent":"第3","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-02-20","time":"22:05:33","likeArticle":false},{"articleId":2,"user":{"userId":4,"userName":"哈哈哈233","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user4.png"},"articleContent":"第一","articleImgs":["http://120.77.87.78:8080/cycle/image/article2_1.jpeg","http://120.77.87.78:8080/cycle/image/article2_2.jpeg","http://120.77.87.78:8080/cycle/image/article2_3.jpeg"],"lookNum":1,"likeNum":1,"commentNum":0,"date":"2017-02-20","time":"22:05:14","likeArticle":false},{"articleId":1,"user":{"userId":1,"userName":"哈哈哈","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user1.png"},"articleContent":"第一","articleImgs":["http://120.77.87.78:8080/cycle/image/article1_1.jpeg","http://120.77.87.78:8080/cycle/image/article1_2.jpeg","http://120.77.87.78:8080/cycle/image/article1_4.jpeg"],"lookNum":12,"likeNum":0,"commentNum":0,"date":"2017-02-20","time":"21:23:22","likeArticle":false}]
     */

    private int code;
    private List<ArticlesBean> articles;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * articleId : 7
         * user : {"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/user10.png"}
         * articleContent : 123
         * articleImgs : []
         * lookNum : 1
         * likeNum : 0
         * commentNum : 0
         * date : 2017-03-03
         * time : 10:07:48
         * likeArticle : false
         */

        private int articleId;
        private UserBean user;
        private String articleContent;
        private int lookNum;
        private int likeNum;
        private int commentNum;
        private String date;
        private String time;
        private boolean likeArticle;
        private List<?> articleImgs;

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getArticleContent() {
            return articleContent;
        }

        public void setArticleContent(String articleContent) {
            this.articleContent = articleContent;
        }

        public int getLookNum() {
            return lookNum;
        }

        public void setLookNum(int lookNum) {
            this.lookNum = lookNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isLikeArticle() {
            return likeArticle;
        }

        public void setLikeArticle(boolean likeArticle) {
            this.likeArticle = likeArticle;
        }

        public List<?> getArticleImgs() {
            return articleImgs;
        }

        public void setArticleImgs(List<?> articleImgs) {
            this.articleImgs = articleImgs;
        }

        public static class UserBean {
            /**
             * userId : 10
             * userName : Rye
             * userPassword : null
             * userEmergencyPhone : null
             * userImg : http://120.77.87.78:8080/cycle/image/user10.png
             */

            private int userId;
            private String userName;
            private Object userPassword;
            private Object userEmergencyPhone;
            private String userImg;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public Object getUserPassword() {
                return userPassword;
            }

            public void setUserPassword(Object userPassword) {
                this.userPassword = userPassword;
            }

            public Object getUserEmergencyPhone() {
                return userEmergencyPhone;
            }

            public void setUserEmergencyPhone(Object userEmergencyPhone) {
                this.userEmergencyPhone = userEmergencyPhone;
            }

            public String getUserImg() {
                return userImg;
            }

            public void setUserImg(String userImg) {
                this.userImg = userImg;
            }
        }
    }
}
