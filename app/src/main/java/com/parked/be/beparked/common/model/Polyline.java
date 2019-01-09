package com.parked.be.beparked.common.model;

import com.google.gson.annotations.SerializedName;

public class Polyline {
    @SerializedName("points") String points;

    public String getPoints() {
        return points;
    }

}
