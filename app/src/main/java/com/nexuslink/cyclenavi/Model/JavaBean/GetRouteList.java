package com.nexuslink.cyclenavi.Model.JavaBean;

import java.util.List;

/**
 * Created by G150S on 2017/4/25.
 */

public class GetRouteList  {

    /**
     * routes : [{"routeId":1,"userId":10,"totalTime":"jbn","date":"rt","route":"re","picture":"http://120.77.87.78:8080/cycle/image/route/route10_rt_1.png","speedList":"sfdv","heightList":"sdv"},{"routeId":2,"userId":10,"totalTime":"56","date":"s","route":"sa","picture":"http://120.77.87.78:8080/cycle/image/route/route10_s_1.jpeg","speedList":"sac","heightList":"sds\r\n\r\n"}]
     * code : 200
     */

    private int code;
    private List<RoutesBean> routes;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class RoutesBean {
        /**
         * routeId : 1
         * userId : 10
         * totalTime : jbn
         * date : rt
         * route : re
         * picture : http://120.77.87.78:8080/cycle/image/route/route10_rt_1.png
         * speedList : sfdv
         * heightList : sdv
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
