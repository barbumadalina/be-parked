package com.parked.be.beparked.screens.utils;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.common.maps.MapBitmapCache;
import com.parked.be.beparked.common.mvp.MvpPresenterImpl;
import com.parked.be.beparked.services.DataProvider;
import com.parked.be.beparked.services.DataProviderImpl;

public class MainPresenterImpl extends MvpPresenterImpl<MainView> implements MainPresenter {
    DataProvider serverUtilities =  DataProviderImpl.getInstance();

    @Override
    public void saveBitmap(final Bitmap bitmap) {
        MapBitmapCache.instance().putBitmap(bitmap);
    }

    @Override
    public void provideMapLatLngBounds(Location lastKnownLocation) {
        LatLngBounds bounds = serverUtilities.provideLatLngBoundsForAllPlaces(lastKnownLocation);
        Log.e("bounds", bounds.toString());
        getView().setMapLatLngBounds(bounds);
    }
}
