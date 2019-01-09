package com.parked.be.beparked.screens.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.common.model.AreaInformation;
import com.parked.be.beparked.common.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface DetailsFragmentView extends MvpView {
    void drawPolylinesOnMap(ArrayList<LatLng> decode);
    void provideAreaData(List<AreaInformation> areaInformationList);
    void onBackPressedWithScene(LatLngBounds latLngBounds);
    void moveMapAndAddMaker(LatLngBounds latLngBounds);
    void updateMapZoomAndRegion(LatLng northeastLatLng, LatLng southwestLatLng);
}