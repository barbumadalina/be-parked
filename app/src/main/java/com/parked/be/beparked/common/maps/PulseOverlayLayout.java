package com.parked.be.beparked.common.maps;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.R;
import com.parked.be.beparked.common.model.AreaInformation;
import com.parked.be.beparked.data.enums.Saturation;

public class PulseOverlayLayout extends MapOverlayLayout {
    private static final int ANIMATION_DELAY_FACTOR = 100;

    private PulseMarkerView startMarker, finishMarker;
    private int scaleAnimationDelay = 100;

    public PulseOverlayLayout(final Context context) {
        this(context, null);
    }

    public PulseOverlayLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.pulse_wrapper_layout, this);
    }

    public void setupMarkers(final Point point, final AreaInformation areaInformation) {
        startMarker = new PulseMarkerView(getContext(), areaInformation, point);
        finishMarker = new PulseMarkerView(getContext(), areaInformation, point);
    }

    public void removeStartMarker() {
        removeMarker(startMarker);
    }

    public void removeFinishMarker() {
        removeMarker(finishMarker);
    }

    public void addStartMarker(final AreaInformation areaInformation) {
        startMarker = createPulseMarkerView(areaInformation);
        startMarker.updatePulseViewLayoutParams(googleMap.getProjection().toScreenLocation(areaInformation.getCenter()));
        addMarker(startMarker);
        startMarker.show();
    }

    public void addFinishMarker(final AreaInformation areaInformation) {
        finishMarker = createPulseMarkerView(areaInformation);
        finishMarker.updatePulseViewLayoutParams(googleMap.getProjection().toScreenLocation(areaInformation.getCenter()));
        addMarker(finishMarker);
        finishMarker.show();
    }

    @NonNull
    private PulseMarkerView createPulseMarkerView(final AreaInformation areaInformation) {
        return new PulseMarkerView(getContext(), areaInformation, googleMap.getProjection().toScreenLocation(areaInformation.getCenter()));
    }

    @NonNull
    private PulseMarkerView createPulseMarkerView(final int position, final Point point, final AreaInformation areaInformation) {
        PulseMarkerView pulseMarkerView = new PulseMarkerView(getContext(), areaInformation, point, position);
        addMarker(pulseMarkerView);
        Log.e("DEBUG", "Marker created " + markersList);
        return pulseMarkerView;
    }

    public void createAndShowMarker(final int position, final AreaInformation areaInformation) {
        LatLng latLng = areaInformation.getCenter();
        PulseMarkerView marker = createPulseMarkerView(position, googleMap.getProjection().toScreenLocation(latLng), areaInformation);
        marker.showWithDelay(scaleAnimationDelay);
        scaleAnimationDelay += ANIMATION_DELAY_FACTOR;
    }

    public void showMarker(final int position) {
         ((PulseMarkerView)markersList.get(position)).pulse();
    }

    public void drawStartAndFinishMarker() {

        AreaInformation ai_1 = new AreaInformation((LatLng)polylines.get(0),1, Saturation.UNKNOWN, "THIS IS A TEST", "testttt");
        AreaInformation ai_2 = new AreaInformation((LatLng)polylines.get(polylines.size() - 1),1, Saturation.UNKNOWN, "THIS IS A TEST", "testttt");
        addStartMarker(ai_1);
        addFinishMarker(ai_2);
        setOnCameraIdleListener(null);
    }

    public void onBackPressed(final LatLngBounds latLngBounds) {
        moveCamera(latLngBounds);
        removeStartAndFinishMarkers();
        removeCurrentPolyline();
        showAllMarkers();
        refresh();
    }

    private void removeStartAndFinishMarkers() {
        removeStartMarker();
        removeFinishMarker();
    }
}
