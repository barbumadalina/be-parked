package com.parked.be.beparked.screens.utils;

import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.common.mvp.MvpView;

public interface MainView extends MvpView {
    void setMapLatLngBounds(final LatLngBounds latLngBounds);
}