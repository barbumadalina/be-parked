package com.parked.be.beparked.services;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.common.model.AreaInformation;
import com.parked.be.beparked.data.enums.Saturation;
import com.parked.be.beparked.services.mocks.MockService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataProviderImpl implements DataProvider {
    private MockService service = new MockService();
    private static LatLng userLastKnownLocation;
    private static List<AreaInformation> lastKnownAreaInfo;

    private static DataProviderImpl instance = new DataProviderImpl();

    public static DataProviderImpl getInstance(){
        return instance;
    }

    private DataProviderImpl(){
    }

    @Override
    public List<AreaInformation> getSurroundingAreaInformation(LatLng currentLocation) {
        Random random = new Random();
        List<LatLng> nearbyLocations = service.getRandomNearbyLocations(currentLocation);
        List<AreaInformation> areaInformation = new ArrayList<>();

        for( LatLng location : nearbyLocations )
            areaInformation.add(new AreaInformation(location,500, Saturation.getRandomSaturation(),"Test" + random.nextInt(), "Some Street"));

        lastKnownAreaInfo = areaInformation;

        return areaInformation;
    }

    /**
     * todo
     * @return
     */
    public LatLngBounds provideLatLngBoundsForAllPlaces(Location lastKnownLocation) {
        if (userLastKnownLocation == null && lastKnownLocation!= null)
            userLastKnownLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

        if ( userLastKnownLocation == null && lastKnownLocation == null)
            Log.e("DEBUG", "Nasol coae");

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (lastKnownAreaInfo == null)
            getSurroundingAreaInformation(userLastKnownLocation);

        for(AreaInformation areaInformation : lastKnownAreaInfo) {
            builder.include(new LatLng(areaInformation.getCenter().latitude, areaInformation.getCenter().longitude));
        }

        return builder.build();
    }

    @Override
    public List<AreaInformation> providePlacesList() {
        if (lastKnownAreaInfo == null)
            Log.e("nu" ,"ai date");
        return lastKnownAreaInfo;
    }
}
