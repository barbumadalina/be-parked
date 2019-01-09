package com.parked.be.beparked.screens.map;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.parked.be.beparked.common.maps.MapsUtil;
import com.parked.be.beparked.common.model.Bounds;
import com.parked.be.beparked.common.model.DirectionsResponse;
import com.parked.be.beparked.common.model.Route;
import com.parked.be.beparked.common.mvp.MvpPresenterImpl;
import com.parked.be.beparked.common.utils.MapsApiManager;
import com.parked.be.beparked.services.DataProvider;
import com.parked.be.beparked.services.DataProviderImpl;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailsFragmentPresenterImpl extends MvpPresenterImpl<DetailsFragmentView> implements DetailsFragmentPresenter {

    private MapsApiManager mapsApiManager = MapsApiManager.instance();
    private DataProvider dataProvider = DataProviderImpl.getInstance();

    @Override
    public void drawRoute(final LatLng first, final int position) {
        LatLng second = dataProvider.providePlacesList().get(position).getCenter();

        mapsApiManager.getRoute(first, second, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try {
                    Route route = new Gson().fromJson(response.body().charStream(), DirectionsResponse.class).getRoutes().get(0);
                    providePolylineToDraw(route.getOverviewPolyline().getPoints());
                    updateMapZoomAndRegion(route.getBounds());
                }
                catch (Exception e){
                    Log.e("DEBUG","No idea");
                }
            }

        });
    }

    @Override
    public void provideAreaData() {
        getView().provideAreaData(dataProvider.providePlacesList());
    }

    @Override
    public void onBackPressedWithScene() {
        getView().onBackPressedWithScene(dataProvider.provideLatLngBoundsForAllPlaces(null));
    }

    @Override
    public void moveMapAndAddMarker() {
        getView().moveMapAndAddMaker(dataProvider.provideLatLngBoundsForAllPlaces(null));
    }

    private void updateMapZoomAndRegion(final Bounds bounds) {
        bounds.getSouthwest().setLat(MapsUtil.increaseLatitude(bounds));
        getView().updateMapZoomAndRegion(bounds.getNortheastLatLng(), bounds.getSouthwestLatLng());
    }

    private void providePolylineToDraw(final String points) {
        getView().drawPolylinesOnMap(new ArrayList<>(PolyUtil.decode(points)));
    }
}
