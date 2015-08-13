package com.thingsle.sameer.thingsle.app;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.adapter.SpinnerCountriesAdapter;
import com.thingsle.sameer.thingsle.data.CitiesData;
import com.thingsle.sameer.thingsle.data.CityCRUD;
import com.thingsle.sameer.thingsle.data.CountrieData;
import com.thingsle.sameer.thingsle.data.CountryCRUD;

public class AddCity extends Activity implements OnClickListener, OnItemSelectedListener {

    public static final String TAG = "AddCityActivity";

    private EditText mTxtCityName;
    private EditText mTxtLongitude;
    private EditText mTxtLatitude;
    private EditText mTxtRating;
    private EditText mTxtThingsTo;
    private Spinner mSpinnerCountry;
    private Button mBtnAdd;

    private CountryCRUD mCountryCrud;
    private CityCRUD mCitiesCrud;

    private CountrieData mSelectedCountry;
    private SpinnerCountriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initViews();

        this.mCountryCrud = new CountryCRUD(this);
        this.mCitiesCrud = new CityCRUD(this);

        //fill the spinner with companies
        List<CountrieData> listCountries = mCountryCrud.getAllCountries();
        if(listCountries != null) {
            mAdapter = new SpinnerCountriesAdapter(this, listCountries);
            mSpinnerCountry.setAdapter(mAdapter);
            mSpinnerCountry.setOnItemSelectedListener(this);
        }
    }

    private void initViews() {
        this.mTxtCityName = (EditText) findViewById(R.id.txt_city_name);
        this.mTxtLongitude = (EditText) findViewById(R.id.txt_longitude);
        this.mTxtLatitude = (EditText) findViewById(R.id.txt_latitude);
        this.mTxtRating = (EditText) findViewById(R.id.txt_rating);
        this.mTxtThingsTo = (EditText) findViewById(R.id.txt_things_to);
        this.mSpinnerCountry = (Spinner) findViewById(R.id.spinner_countries);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable cityName = mTxtCityName.getText();
                Editable editableLongitude = mTxtLongitude.getText();
                Editable editableLatitude = mTxtLatitude.getText();
                Editable rating = mTxtRating.getText();
                Editable thingsTo = mTxtThingsTo.getText();

                mSelectedCountry = (CountrieData) mSpinnerCountry.getSelectedItem();
                if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(editableLongitude)
                        && !TextUtils.isEmpty(editableLatitude) && !TextUtils.isEmpty(rating)
                        && !TextUtils.isEmpty(thingsTo) && mSelectedCountry != null
                        ) {
                    // add the city to database
                    double latitude = Double.valueOf(editableLatitude.toString());
                    double longitude = Double.valueOf(editableLongitude.toString());

                    CitiesData createdCity = mCitiesCrud.createCity(cityName.toString(),
                    longitude,
                    latitude, rating.toString(),
                    thingsTo.toString(),
                    mSelectedCountry.getId());

                    Toast.makeText(this, "City created successfully", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "added city : "+ createdCity.getName()+"to country : "+createdCity.getCountrieData().getId());
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast.makeText(this,"One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountryCrud.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedCountry = mAdapter.getItem(position);
        Log.d(TAG, "selectedCountry : "+mSelectedCountry.getName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
