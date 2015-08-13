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


public class ListCitiesAdapter extends BaseAdapter {

    public static final String TAG = "ListCitiesAdapter";

    private List<CitiesData> mItems;
    private LayoutInflater mInflater;

    public ListCitiesAdapter(Context context, List<CitiesData> listCities) {
        this.setItems(listCities);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public CitiesData getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getCity_id() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_emloyee, parent, false);
            holder = new ViewHolder();
            holder.txtCityName = (TextView) v.findViewById(R.id.txt_employee_name);
            holder.txtLongitude = (TextView) v.findViewById(R.id.txt_company_name);
            holder.txtLatitude = (TextView) v.findViewById(R.id.txt_address);
            holder.txtRating = (TextView) v.findViewById(R.id.txt_phone_number);
            holder.txtThingsTo = (TextView) v.findViewById(R.id.txt_email);
            holder.txtCountryName = (TextView) v.findViewById(R.id.txt_salary);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        CitiesData currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtCityName.setText(currentItem.getName());
            holder.txtLongitude.setText(String.valueOf(currentItem.getLongi())+" °");
            holder.txtLatitude.setText(String.valueOf(currentItem.getLat())+" °");
            holder.txtRating.setText(currentItem.getRating());
            holder.txtThingsTo.setText(currentItem.getThingsTO());
            holder.txtCountryName.setText(currentItem.getCountrieData().getName());

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
        TextView txtCountryName;
    }

}
