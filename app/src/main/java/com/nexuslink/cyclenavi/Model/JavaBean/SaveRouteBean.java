package com.nexuslink.cyclenavi.Model.JavaBean;

/**
 * Created by Rye on 2017/4/30.
 */

public class SaveRouteBean {

    /**
     * code : 200
     * route : {"routeId":0,"userId":10,"totalTime":"56","date":"s","route":"sa","picture":"route10_s_1.jpeg","speedList":"sac","heightList":"sds\r\n\r\n"}
     */

    private int code;
    private RouteBean route;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public static class RouteBean {
        /**
         * routeId : 0
         * userId : 10
         * totalTime : 56
         * date : s
         * route : sa
         * picture : route10_s_1.jpeg
         * speedList : sac
         * heightList : sds


         */

        private int routeId;
        private int userId;
        private String totalTime;
        private String date;
        private String route;
        private String picture;
        private String speedList;
        private String heightList;

        public int getRouteId() {
            return routeId;
        }

        public void setRouteId(int routeId) {
            this.routeId = routeId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getSpeedList() {
            return speedList;
        }

        public void setSpeedList(String speedList) {
            this.speedList = speedList;
        }

        public String getHeightList() {
            return heightList;
        }

        public void setHeightList(String heightList) {
            this.heightList = heightList;
        }
    }
}
