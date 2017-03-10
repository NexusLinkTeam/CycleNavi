package com.nexuslink.cyclenavi.Model.JavaBean;

import java.util.List;

/**
 * Created by Rye on 2017/3/3.
 */
public class GetCommentsBean {

    /**
     * code : 200
     * comments : [{"commentId":6,"articleId":7,"user":{"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"user10.png"},"content":"postman","floor":1,"date":"2017-03-03","time":"10:26:56"}]
     */

    private int code;
    private List<CommentsBean> comments;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * commentId : 6
         * articleId : 7
         * user : {"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":null,"userImg":"user10.png"}
         * content : postman
         * floor : 1
         * date : 2017-03-03
         * time : 10:26:56
         */

        private int commentId;
        private int articleId;
        private UserBean user;
        private String content;
        private int floor;
        private String date;
        private String time;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
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

        public static class UserBean {
            /**
             * userId : 10
             * userName : Rye
             * userPassword : null
             * userEmergencyPhone : null
             * userImg : user10.png
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
