package com.parked.be.beparked.common.model;

import com.google.android.gms.maps.model.LatLng;
import com.parked.be.beparked.data.enums.Saturation;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AreaInformation {
    private LatLng center;
    private double radius;
    private Saturation saturation;
    private String description;
    private String streetName;
    private Date lastUpdate;
    private int numberOfVehicles;

    public AreaInformation(LatLng center, double radius, Saturation saturation, String description, String streetName) {
        Random random = new Random();

        this.center = center;
        this.radius = radius;
        this.saturation = saturation;

        // todo: remove this and base on data from server
        switch (saturation) {
            case LOW:
                this.numberOfVehicles = random.nextInt(10);
                break;
            case MEDIUM:
                this.numberOfVehicles = random.nextInt(10) + 10;
                break;
            case HIGH:
                this.numberOfVehicles = random.nextInt(30) + 20;
                break;
            default: this.numberOfVehicles = 0;
        }

        this.description = description;
        this.streetName = streetName;
        this.lastUpdate = Calendar.getInstance().getTime();
    }

    public LatLng getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Saturation getSaturation() {
        return saturation;
    }

    public String getDescription() {
        return description;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
