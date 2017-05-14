package com.nexuslink.cyclenavi.Model.JavaBean;

/**
 * Created by Rye on 2017/5/12.
 */

public class GetUserInfoBean extends BaseBean{

    /**
     * code : 200
     * user : {"userId":10,"userName":"Rye","userPassword":null,"userEmergencyPhone":"13540817427","userImg":"http://120.77.87.78:8080/cycle/image/default.jpg"}
     */

    private int code;
    private UserBean user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * userId : 10
         * userName : Rye
         * userPassword : null
         * userEmergencyPhone : 13540817427
         * userImg : http://120.77.87.78:8080/cycle/image/default.jpg
         */

        private int userId;
        private String userName;
        private Object userPassword;
        private String userEmergencyPhone;
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

        public String getUserEmergencyPhone() {
            return userEmergencyPhone;
        }

        public void setUserEmergencyPhone(String userEmergencyPhone) {
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
