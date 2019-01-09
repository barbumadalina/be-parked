package com.parked.be.beparked.screens.map;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parked.be.beparked.R;
import com.parked.be.beparked.common.maps.MapBitmapCache;
import com.parked.be.beparked.common.maps.PulseOverlayLayout;
import com.parked.be.beparked.common.model.AreaInformation;
import com.parked.be.beparked.common.mvp.MvpFragment;
import com.parked.be.beparked.common.transitions.ScaleDownImageTransition;
import com.parked.be.beparked.common.transitions.TransitionUtils;
import com.parked.be.beparked.common.utils.AppConstants;
import com.parked.be.beparked.common.views.HorizontalRecyclerViewScrollListener;
import com.parked.be.beparked.common.views.TranslateItemAnimator;
import com.parked.be.beparked.screens.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailsFragment extends MvpFragment<DetailsFragmentView, DetailsFragmentPresenter>
        implements DetailsFragmentView, OnMapReadyCallback, AreaInformationAdapter.OnPlaceClickListener, HorizontalRecyclerViewScrollListener.OnItemCoverListener {
    public static final String TAG = DetailsFragment.class.getSimpleName();

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.container)
    FrameLayout containerLayout;
    @BindView(R.id.mapOverlayLayout)
    PulseOverlayLayout mapOverlayLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<AreaInformation> areaInformationList;
    private AreaInformationAdapter areaInformationAdapter;
    private String currentTransitionName;
    private Scene detailsScene;

    public static Fragment newInstance(final Context ctx) {
        DetailsFragment fragment = new DetailsFragment();
        ScaleDownImageTransition transition = new ScaleDownImageTransition(ctx, MapBitmapCache.instance().getBitmap());
        transition.addTarget(ctx.getString(R.string.mapPlaceholderTransition));
        transition.setDuration(600);
        fragment.setEnterTransition(transition);
        return fragment;
    }

    @Override
    protected DetailsFragmentPresenter createPresenter() {
        return new DetailsFragmentPresenterImpl();
    }

    @Override
    public void onBackPressed() {
        if (detailsScene != null) {
            presenter.onBackPressedWithScene();
        } else {
            ((MainActivity) getActivity()).superOnBackPressed();
        }
    }

    private View getSharedViewByPosition(final int childPosition) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            if (childPosition == recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i))) {
                return recyclerView.getChildAt(i);
            }
        }
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData();
        setupMapFragment();
        setupRecyclerView();

    }

    private void setupData() {
        presenter.provideAreaData();
    }

    private void setupMapFragment() {
        ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        areaInformationAdapter = new AreaInformationAdapter(this, getActivity());
        recyclerView.setAdapter(areaInformationAdapter);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        addDataToRecyclerView();
        mapOverlayLayout.setupMap(googleMap);
        setupGoogleMap();
    }

    private void setupGoogleMap() {
        presenter.moveMapAndAddMarker();
    }

    private void addDataToRecyclerView() {
        recyclerView.setItemAnimator(new TranslateItemAnimator());
        recyclerView.setAdapter(areaInformationAdapter);
        areaInformationAdapter.setPlacesList(areaInformationList);
        recyclerView.addOnScrollListener(new HorizontalRecyclerViewScrollListener(this));
    }

    @Override
    public void onPlaceClicked(final View sharedView, final String transitionName, final int position) {
        currentTransitionName = transitionName;

        detailsScene = DetailsLayout.showScene(getActivity(), containerLayout, sharedView, transitionName, areaInformationList.get(position));
        drawRoute(position);
        hideAllMarkers();
    }

    private void drawRoute(final int position) {
        presenter.drawRoute(mapOverlayLayout.getCurrentLatLng(), position);
    }

    private void hideAllMarkers() {
        mapOverlayLayout.setOnCameraIdleListener(null);
        mapOverlayLayout.hideAllMarkers();
    }

    @Override
    public void drawPolylinesOnMap(final ArrayList<LatLng> polylines) {
        getActivity().runOnUiThread(() -> mapOverlayLayout.addPolyline(polylines));
    }

    @Override
    public void provideAreaData(final List<AreaInformation> list) {
        this.areaInformationList = list;
    }

    @Override
    public void onBackPressedWithScene(final LatLngBounds latLngBounds) {
        int childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName);
        DetailsLayout.hideScene(getActivity(), containerLayout, getSharedViewByPosition(childPosition), currentTransitionName);
        notifyLayoutAfterBackPress(childPosition);
        mapOverlayLayout.onBackPressed(latLngBounds);
        detailsScene = null;
    }

    private void notifyLayoutAfterBackPress(final int childPosition) {
        containerLayout.removeAllViews();
        containerLayout.addView(recyclerView);
        recyclerView.requestLayout();
        areaInformationAdapter.notifyItemChanged(childPosition);
    }

    @Override
    public void moveMapAndAddMaker(final LatLngBounds latLngBounds) {
        if (areaInformationList == null){
            Log.d(AppConstants.D_AREA_INFO_LIST, "Area Information List is empty.");
            setupData();
        }

        mapOverlayLayout.moveCamera(latLngBounds);
        mapOverlayLayout.setOnCameraIdleListener(() -> {
            for (int i = 0; i < areaInformationList.size(); i++) {
                mapOverlayLayout.setupMarkers(new Point(1,2), areaInformationList.get(i));
                mapOverlayLayout.createAndShowMarker(i, areaInformationList.get(i));
            }
            mapOverlayLayout.setOnCameraIdleListener(null);
        });
        mapOverlayLayout.setOnCameraMoveListener(mapOverlayLayout::refresh);
    }

    @Override
    public void updateMapZoomAndRegion(final LatLng northeastLatLng, final LatLng southwestLatLng) {
        getActivity().runOnUiThread(() -> {
            mapOverlayLayout.animateCamera(new LatLngBounds(southwestLatLng, northeastLatLng));
            mapOverlayLayout.setOnCameraIdleListener(() -> mapOverlayLayout.drawStartAndFinishMarker());
        });
    }

    @Override
    public void onItemCover(final int position) {
        try {
            mapOverlayLayout.showMarker(position);
        }
        catch (Exception e){
            // do nothing, basically it gives out an error the first time because the items aren't loaded
            // todo = this can probably be fixed - use magic
        }
    }
}
