package com.parked.be.beparked.common.utils;

import com.google.android.gms.maps.model.LatLng;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class MapsApiManager {
    private static final String ORIGIN = "origin";
    private static final String DESTINATION = "destination";
    private static final String KEY = "key";

    private static MapsApiManager sInstance;
    private OkHttpClient mClient;

    public static MapsApiManager instance() {
        if(sInstance == null) {
            sInstance = new MapsApiManager();
            sInstance.initialize();
            return sInstance;
        } else {
            return sInstance;
        }
    }

    private MapsApiManager(){}

    public void initialize() {
        mClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
    }

    public void getRoute(final LatLng start, final LatLng end, final Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(RestConstants.GOOGLE_MAPS_API_BASE_URL).newBuilder();

        urlBuilder.addQueryParameter(ORIGIN, start.latitude + "," + start.longitude);
        urlBuilder.addQueryParameter(DESTINATION, end.latitude + "," + end.longitude);
        urlBuilder.addQueryParameter(KEY, RestConstants.GOOGLE_MAPS_API_KEY);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        mClient.newCall(request).enqueue(callback);
    }
}
