package com.thingsle.sameer.thingsle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.data.PlacesData;

import java.util.List;

/**
 * Created by Sameer on 8/13/2015.
 */
public class ListPlacesAdapter  extends BaseAdapter {

    public static final String TAG = "ListPlacesAdapter";

    private List<PlacesData> mItems;
    private LayoutInflater mInflater;

    public ListPlacesAdapter(Context context, List<PlacesData> listPlaces) {
        this.setItems(listPlaces);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public PlacesData getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getPlace_id(): position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_places, parent, false);
            holder = new ViewHolder();
            holder.txtCityName = (TextView) v.findViewById(R.id.txt_place_name);
            holder.txtLongitude = (TextView) v.findViewById(R.id.txt_longitude);
            holder.txtLatitude = (TextView) v.findViewById(R.id.txt_latitude);
            holder.txtRating = (TextView) v.findViewById(R.id.txt_rating);
            holder.txtDescription = (TextView) v.findViewById(R.id.txt_description);
            holder.txtDetails = (TextView) v.findViewById(R.id.txt_details);
            holder.txtStatus = (TextView) v.findViewById(R.id.txt_status);
            holder.txtCityName = (TextView) v.findViewById(R.id.txt_city_name);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        PlacesData currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtPlaceName.setText(currentItem.getName());
            holder.txtLongitude.setText(String.valueOf(currentItem.getLongi())+" °");
            holder.txtLatitude.setText(String.valueOf(currentItem.getLat())+" °");
            holder.txtRating.setText(currentItem.getRating());
            holder.txtDescription.setText(currentItem.getDescription());
            holder.txtDetails.setText(currentItem.getDetails());
            holder.txtStatus.setText(String.valueOf(currentItem.getStatus()));
            holder.txtCityName.setText(currentItem.getCitiesData().getName());

        }

        return v;
    }

    public List<PlacesData> getItems() {
        return mItems;
    }

    public void setItems(List<PlacesData> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtPlaceName;
        TextView txtLongitude;
        TextView txtLatitude;
        TextView txtRating;
        TextView txtDescription;
        TextView txtDetails;
        TextView txtStatus;
        TextView txtCityName;
    }

}

