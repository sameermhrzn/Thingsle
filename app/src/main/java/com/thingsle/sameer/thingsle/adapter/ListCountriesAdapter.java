package com.thingsle.sameer.thingsle.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.data.CountrieData;

import java.util.List;


/**
 * Created by Sameer on 8/13/2015.
 */
public class ListCountriesAdapter extends BaseAdapter {

    public static final String TAG = "ListCountriesAdapter";

    private List<CountrieData> mItems;
    private LayoutInflater mInflater;

    public ListCountriesAdapter(Context context, List<CountrieData> listCountries) {
        this.setItems(listCountries);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0;
    }

    @Override
    public CountrieData getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            v = mInflater.inflate(R.layout.list_item_country, parent, false);
            holder = new ViewHolder();
            holder.txtCountryName = (TextView) v.findViewById(R.id.txt_country_name);
            holder.txtLatitude = (TextView) v.findViewById(R.id.txt_latitude);
            holder.txtLongitude = (TextView) v.findViewById(R.id.txt_longitude);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        CountrieData currentItem = getItem(position);
        if (currentItem != null) {
            holder.txtCountryName.setText(currentItem.getName());
            holder.txtLatitude.setText(String.valueOf(currentItem.getLat()) + " °");
            holder.txtLongitude.setText(String.valueOf(currentItem.getLongi()) + " °");
        }

        return v;
    }

    public List<CountrieData> getItems() {
        return mItems;
    }

    public void setItems(List<CountrieData> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtCountryName;
        TextView txtLatitude;
        TextView txtLongitude;
    }

}

