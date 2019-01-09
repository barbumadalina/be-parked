package com.parked.be.beparked.screens.map;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parked.be.beparked.R;
import com.parked.be.beparked.common.model.AreaInformation;
import com.parked.be.beparked.common.transitions.TransitionUtils;
import com.parked.be.beparked.common.utils.GuiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AreaInformationAdapter extends RecyclerView.Adapter<AreaInformationAdapter.AreaInformationViewHolder> {

    private final OnPlaceClickListener listener;
    private Context context;
    private List<AreaInformation> areaInformationList = new ArrayList<>();

    AreaInformationAdapter(OnPlaceClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public AreaInformationViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new AreaInformationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_place, parent, false));
    }

    @Override
    public void onBindViewHolder(final AreaInformationViewHolder holder, final int position) {
        holder.streetName.setText(areaInformationList.get(position).getStreetName());
        holder.numberOfVehicles.setTextColor(ContextCompat.getColor(context,areaInformationList.get(position).getSaturation().toColor()));
        holder.numberOfVehicles.setText("Aprox. " +  areaInformationList.get(position).getNumberOfVehicles() + " cars");
        holder.lastUpdated.setText(GuiUtils.lastUpdatedFromDate(areaInformationList.get(position).getLastUpdate()));
        holder.placePhoto.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_parking));
        holder.root.setOnClickListener(view -> listener.onPlaceClicked(holder.root, TransitionUtils.getRecyclerViewTransitionName(position), position));
    }

    @Override
    public int getItemCount() {
        return areaInformationList.size();
    }

    public List<AreaInformation> getAreaInformationList() {
        return areaInformationList;
    }

    void setPlacesList(List<AreaInformation> areaInformationList) {
        this.areaInformationList = areaInformationList;
        for (int i = 0; i < areaInformationList.size(); i++) {
            notifyItemInserted(i);
        }
    }

    interface OnPlaceClickListener {
        void onPlaceClicked(View sharedView, String transitionName, final int position);
    }

    static class AreaInformationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.street_name) TextView streetName;
        @BindView(R.id.number_of_vehicles) TextView numberOfVehicles;
        @BindView(R.id.last_updated) TextView lastUpdated;
        @BindView(R.id.root) CardView root;
        @BindView(R.id.headerImage) ImageView placePhoto;

        AreaInformationViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
