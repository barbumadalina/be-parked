package com.parked.be.beparked.services;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.common.model.AreaInformation;

import java.util.List;

/**
 * Used for any resources that come from the server.
 */
public interface DataProvider {
    List<AreaInformation> getSurroundingAreaInformation(LatLng currentLocation);
    LatLngBounds provideLatLngBoundsForAllPlaces(Location location);
    List<AreaInformation> providePlacesList();
}
