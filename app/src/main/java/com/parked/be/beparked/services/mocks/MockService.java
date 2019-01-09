package com.parked.be.beparked.services.mocks;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * TODO: this will be removed when logic is moved on the server side
 */
public class MockService {
    private final int numberOfRandomLocations = 3;
    private final int radiusInMeters = 1500;

    public List<LatLng> getRandomNearbyLocations(LatLng coordinates){
        Random random = new Random();
        List<LatLng> locationList = new LinkedList();

        // Convert radius from meters to degrees
        double radiusInDegrees = radiusInMeters / 111000f;

        double x0 = coordinates.longitude;
        double y0 = coordinates.latitude;

        for ( int i = 0; i < numberOfRandomLocations; i++ ) {
            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(Math.toRadians(y0));

            double foundLongitude = new_x + x0;
            double foundLatitude = y + y0;

            locationList.add(new LatLng(foundLatitude, foundLongitude));

            System.out.println("Longitude: " + foundLongitude + "  Latitude: " + foundLatitude);
        }

        return locationList;
    }
}
