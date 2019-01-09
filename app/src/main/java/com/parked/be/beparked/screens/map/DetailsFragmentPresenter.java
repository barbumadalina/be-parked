package com.parked.be.beparked.screens.map;

import com.google.android.gms.maps.model.LatLng;
import com.parked.be.beparked.common.mvp.MvpPresenter;

public interface DetailsFragmentPresenter extends MvpPresenter<DetailsFragmentView> {
    void drawRoute(LatLng first, int position);
    void provideAreaData();
    void onBackPressedWithScene();
    void moveMapAndAddMarker();
}