package com.nexuslink.cyclenavi.Util;

/**
 * Created by Rye on 2017/2/21.
 */
public class History {
    private double latitude;
    private double longitude;
    private String name;
    private String district;

    public double getLatitude() {
        return latitude;
    }

    public History(double latitude,double longitude, String name, String district) {
        this.latitude = latitude;
        this.name = name;
        this.longitude = longitude;
        this.district = district;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDistrict() {
        return district;
    }

    public String getName() {
        return name;
    }
}
