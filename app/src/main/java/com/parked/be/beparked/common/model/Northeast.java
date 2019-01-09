package com.parked.be.beparked.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Response object from Google MAPS API
 */
public class Northeast {

    @SerializedName("lat") String lat;
    @SerializedName("lng") String lng;

    public String getLat() {
        return lat;
    }

    public Double getLatD() {
        return Double.parseDouble(lat);
    }

    public String getLng() {
        return lng;
    }
}