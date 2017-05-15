package com.nexuslink.cyclenavi.Model.JavaBean;

import java.util.List;

/**
 * Created by G150S on 2017/4/30.
 */

public class GetHisMore {

    /**
     * code : 200
     * articles : [{"articleId":41,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"y","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"17:01:11","likeArticle":false},{"articleId":40,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"dd","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"17:00:09","likeArticle":false},{"articleId":39,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"","articleImgs":["http://120.77.87.78:8080/cycle/image/article39_1.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:56:18","likeArticle":false},{"articleId":38,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"测试","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:50:56","likeArticle":false},{"articleId":37,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"测试","articleImgs":["http://120.77.87.78:8080/cycle/image/article37_1.png","http://120.77.87.78:8080/cycle/image/article37_2.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:26:52","likeArticle":false},{"articleId":36,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"","articleImgs":["http://120.77.87.78:8080/cycle/image/article36_1.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:25:27","likeArticle":false},{"articleId":35,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"","articleImgs":["http://120.77.87.78:8080/cycle/image/article35_1.png","http://120.77.87.78:8080/cycle/image/article35_2.png","http://120.77.87.78:8080/cycle/image/article35_3.png","http://120.77.87.78:8080/cycle/image/article35_4.png","http://120.77.87.78:8080/cycle/image/article35_5.png","http://120.77.87.78:8080/cycle/image/article35_6.png","http://120.77.87.78:8080/cycle/image/article35_7.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:15:29","likeArticle":false},{"articleId":34,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"","articleImgs":[],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:09:17","likeArticle":false},{"articleId":33,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"","articleImgs":["http://120.77.87.78:8080/cycle/image/article33_1.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:09:01","likeArticle":false},{"articleId":32,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"},"articleContent":"测试","articleImgs":["http://120.77.87.78:8080/cycle/image/article32_1.png"],"lookNum":0,"likeNum":0,"commentNum":0,"date":"2017-04-17","time":"16:08:41","likeArticle":false}]
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
         * articleId : 41
         * user : {"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"}
         * articleContent : y
         * articleImgs : []
         * lookNum : 0
         * likeNum : 0
         * commentNum : 0
         * date : 2017-04-17
         * time : 17:01:11
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
             * userImg : http://120.77.87.78:8080/cycle/image/default.jpg
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
