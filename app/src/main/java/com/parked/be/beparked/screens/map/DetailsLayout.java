package com.parked.be.beparked.screens.map;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parked.be.beparked.R;
import com.parked.be.beparked.common.model.AreaInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsLayout extends CoordinatorLayout {

    @BindView(R.id.cardview)
    CardView cardViewContainer;
    @BindView(R.id.headerImage)
    ImageView imageViewPlaceDetails;
    @BindView(R.id.title)
    public TextView textViewTitle;
    @BindView(R.id.description) TextView textViewDescription;

    public DetailsLayout(final Context context) {
        this(context, null);
    }

    public DetailsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    private void setData(AreaInformation place) {
        textViewTitle.setText(place.getStreetName());
        textViewDescription.setText(place.getDescription());
    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName, final AreaInformation data) {
        DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater().inflate(R.layout.item_place, container, false);
        detailsLayout.setData(data);

        TransitionSet set = new ShowDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }

    public static Scene hideScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        DetailsLayout detailsLayout = (DetailsLayout) container.findViewById(R.id.street_parking_details_container);

        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }
}
