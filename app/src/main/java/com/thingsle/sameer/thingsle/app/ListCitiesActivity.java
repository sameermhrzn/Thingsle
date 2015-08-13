package com.thingsle.sameer.thingsle.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.adapter.ListCitiesAdapter;
import com.thingsle.sameer.thingsle.data.CitiesData;
import com.thingsle.sameer.thingsle.data.CityCRUD;

public class ListCitiesActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListCitiesActivity";

    public static final int REQUEST_CODE_ADD_CITY = 40;
    public static final String EXTRA_ADDED_CITY = "extra_key_added_city";
    public static final String EXTRA_SELECTED_COUNTRY_ID = "extra_key_selected_country_id";

    private ListView mListViewCities;
    private TextView mTxtEmptyListCities;
    private ImageButton mBtnAddCity;

    private ListCitiesAdapter mAdapter;
    private List<CitiesData> mListCities;
    private CityCRUD mCityCrud;

    private long mCountryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cities);

        // initialize views
        initViews();

        // get the country id from extras
        mCityCrud = new CityCRUD(this);
        Intent intent  = getIntent();
        if(intent != null) {
            this.mCountryId = intent.getLongExtra(EXTRA_SELECTED_COUNTRY_ID, -1);
        }

        if(mCountryId != -1) {
            mListCities = mCityCrud.getCitiesOfCountry(mCountryId);
            // fill the listView
            if(mListCities != null && !mListCities.isEmpty()) {
                mAdapter = new ListCitiesAdapter(this, mListCities);
                mListViewCities.setAdapter(mAdapter);
            }
            else {
                mTxtEmptyListCities.setVisibility(View.VISIBLE);
                mListViewCities.setVisibility(View.GONE);
            }
        }
    }

    private void initViews() {
        this.mListViewCities = (ListView) findViewById(R.id.list_cities);
        this.mTxtEmptyListCities = (TextView) findViewById(R.id.txt_empty_list_cities);
        this.mBtnAddCity = (ImageButton) findViewById(R.id.btn_add_city);
        this.mListViewCities.setOnItemClickListener(this);
        this.mListViewCities.setOnItemLongClickListener(this);
        this.mBtnAddCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_city:
                Intent intent = new Intent(this, AddCity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CITY);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_CITY) {
            if(resultCode == RESULT_OK) {
                //refresh the listView
                if(mListCities == null || !mListCities.isEmpty()) {
                    mListCities = new ArrayList<CitiesData>();
                }

                if(mCityCrud == null)
                    mCityCrud = new CityCRUD(this);
                mListCities = mCityCrud.getCitiesOfCountry(mCountryId);

                if(mListCities != null && !mListCities.isEmpty() &&
                        mListViewCities.getVisibility() != View.VISIBLE) {
                    mTxtEmptyListCities.setVisibility(View.GONE);
                    mListViewCities.setVisibility(View.VISIBLE);
                }

                if(mAdapter == null) {
                    mAdapter = new ListCitiesAdapter(this, mListCities);
                    mListViewCities.setAdapter(mAdapter);
                }
                else {
                    mAdapter.setItems(mListCities);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCityCrud.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CitiesData clickedCity = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedCity.getName());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CitiesData clickedCity = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+clickedCity.getName());

        showDeleteDialogConfirmation(clickedCity);
        return true;
    }

    private void showDeleteDialogConfirmation(final CitiesData city) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the city \""
                        + city.getName());

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the employee and refresh the list
                if (mCityCrud != null) {
                    mCityCrud.deleteCity(city);

                    //refresh the listView
                    mListCities.remove(city);
                    if (mListCities.isEmpty()) {
                        mListViewCities.setVisibility(View.GONE);
                        mTxtEmptyListCities.setVisibility(View.VISIBLE);
                    }

                    mAdapter.setItems(mListCities);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListCitiesActivity.this, "City deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });

        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
}
