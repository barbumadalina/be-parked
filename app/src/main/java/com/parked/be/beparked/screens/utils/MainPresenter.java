package com.parked.be.beparked.screens.utils;

import android.graphics.Bitmap;
import android.location.Location;

import com.parked.be.beparked.common.mvp.MvpPresenter;

public interface MainPresenter extends MvpPresenter<MainView> {
    void saveBitmap(Bitmap googleMap);
    void provideMapLatLngBounds(Location lastKnownLocation);
}