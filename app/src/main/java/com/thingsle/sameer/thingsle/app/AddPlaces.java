package com.thingsle.sameer.thingsle.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.adapter.SpinnerCitiesAdapter;
import com.thingsle.sameer.thingsle.data.CitiesData;
import com.thingsle.sameer.thingsle.data.CityCRUD;
import com.thingsle.sameer.thingsle.data.CountrieData;
import com.thingsle.sameer.thingsle.data.PlacesCRUD;
import com.thingsle.sameer.thingsle.data.PlacesData;

import java.util.List;

/**
 * Created by Sameer on 8/13/2015.
 */
public class AddPlaces extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "AddPlaceActivity";

    private EditText mTxtPlaceName;
    private EditText mTxtLongitude;
    private EditText mTxtLatitude;
    private EditText mTxtRating;
    private EditText mTxtDescription;
    private EditText mTxtDetails;
    private EditText mTxtStatus;
    private Spinner mSpinnerCity;
    private Button mBtnAdd;

    private PlacesCRUD mPlacesCrud;
    private CityCRUD mCitiesCrud;

    private CitiesData mSelectedCity;
    private SpinnerCitiesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initViews();

        this.mCitiesCrud = new CityCRUD(this);
        this.mPlacesCrud = new PlacesCRUD(this);

        //fill the spinner with cities
        List<CitiesData> listCities = mCitiesCrud.getAllCities();
        if (listCities != null) {
            mAdapter = new SpinnerCitiesAdapter(this, listCities);
            mSpinnerCity.setAdapter(mAdapter);
            mSpinnerCity.setOnItemSelectedListener(this);
        }
    }

    private void initViews() {
        this.mTxtPlaceName = (EditText) findViewById(R.id.txt_place_name);
        this.mTxtLongitude = (EditText) findViewById(R.id.txt_longitude);
        this.mTxtLatitude = (EditText) findViewById(R.id.txt_latitude);
        this.mTxtRating = (EditText) findViewById(R.id.txt_rating);
        this.mTxtDescription = (EditText) findViewById(R.id.txt_description);
        this.mTxtDetails = (EditText) findViewById(R.id.txt_details);
        this.mTxtStatus = (EditText) findViewById(R.id.txt_status);
        this.mSpinnerCity = (Spinner) findViewById(R.id.spinner_cities);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable placeName = mTxtPlaceName.getText();
                Editable editableLongitude = mTxtLongitude.getText();
                Editable editableLatitude = mTxtLatitude.getText();
                Editable rating = mTxtRating.getText();
                Editable description = mTxtDescription.getText();
                Editable details = mTxtDetails.getText();
                Editable status = mTxtStatus.getText();

                mSelectedCity = (CitiesData) mSpinnerCity.getSelectedItem();
                if (!TextUtils.isEmpty(placeName)
                        && !TextUtils.isEmpty(editableLongitude)
                        && !TextUtils.isEmpty(editableLatitude)
                        && !TextUtils.isEmpty(rating)
                        && !TextUtils.isEmpty(description)
                        && !TextUtils.isEmpty(details)
                        && !TextUtils.isEmpty(status)
                        && mSelectedCity != null
                        ) {
                    // add the city to database
                    double latitude = Double.valueOf(editableLatitude.toString());
                    double longitude = Double.valueOf(editableLongitude.toString());
                    boolean bStatus = Boolean.valueOf(status.toString());

                    PlacesData createdPlace = mPlacesCrud.createPlace(placeName.toString(),
                            longitude,
                            latitude,
                            rating.toString(),
                            description.toString(),
                            details.toString(),
                            bStatus,
                            mSelectedCity.getCity_id());

                    Toast.makeText(this, "Place created successfully", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "added Place : " + createdPlace.getName() + "to City : " + createdPlace.getCitiesData().getCity_id());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCitiesCrud.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedCity = mAdapter.getItem(position);
        Log.d(TAG, "selectedCountry : " + mSelectedCity.getName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

