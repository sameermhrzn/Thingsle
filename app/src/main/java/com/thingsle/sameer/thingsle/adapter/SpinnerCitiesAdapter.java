package com.thingsle.sameer.thingsle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.data.CitiesData;

import java.util.List;

/**
 * Created by Sameer on 8/14/2015.
 */
public class SpinnerCitiesAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerPlacesAdapter";

    private List<CitiesData> mItems;
    private LayoutInflater mInflater;

    public SpinnerCitiesAdapter(Context context, List<CitiesData> listCities) {
        this.setItems(listCities);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0;
    }

    @Override
    public CitiesData getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getCity_id() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            v = mInflater.inflate(R.layout.spinner_item_city, parent, false);
            holder = new ViewHolder();
            holder.txtCityName = (TextView) v.findViewById(R.id.txt_city_name);
            holder.txtLongitude = (TextView) v.findViewById(R.id.txt_longitude);
            holder.txtLatitude = (TextView) v.findViewById(R.id.txt_latitude);
            holder.txtRating = (TextView) v.findViewById(R.id.txt_rating);
            holder.txtThingsTo = (TextView) v.findViewById(R.id.txt_things_to);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        CitiesData currentItem = getItem(position);
        if (currentItem != null) {
            holder.txtCityName.setText(currentItem.getName());
            holder.txtLongitude.setText(String.valueOf(currentItem.getLongi()) + " °");
            holder.txtLatitude.setText(String.valueOf(currentItem.getLat()) + " °");
            holder.txtRating.setText(currentItem.getRating());
            holder.txtThingsTo.setText(currentItem.getThingsTO());

        }

        return v;
    }

    public List<CitiesData> getItems() {
        return mItems;
    }

    public void setItems(List<CitiesData> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtCityName;
        TextView txtLongitude;
        TextView txtLatitude;
        TextView txtRating;
        TextView txtThingsTo;


    }
}

