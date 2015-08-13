package com.thingsle.sameer.thingsle.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thingsle.sameer.thingsle.R;
import com.thingsle.sameer.thingsle.adapter.ListCountriesAdapter;
import com.thingsle.sameer.thingsle.data.CountrieData;
import com.thingsle.sameer.thingsle.data.CountryCRUD;

import java.io.Serializable;

/**
 * Created by Sameer on 8/12/2015.
 */
public class AddCountry extends Activity implements View.OnClickListener {

    public static final String TAG = "AddCountryActivity";

    private EditText mTxtCountryName;
    private EditText mTxtLatitude;
    private EditText mTxtLongitude;
    private Button mBtnAdd;

    private CountryCRUD mCountryCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);

        initViews();

        this.mCountryCrud = new CountryCRUD(this);
    }

    private void initViews() {
        this.mTxtCountryName = (EditText) findViewById(R.id.txt_country_name);
        this.mTxtLatitude = (EditText) findViewById(R.id.txt_latitude);
        this.mTxtLongitude = (EditText) findViewById(R.id.txt_longitude);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable countryName = mTxtCountryName.getText();
                Editable strlatitude = mTxtLatitude.getText();
                Editable strlongitude = mTxtLongitude.getText();
                if (!TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(strlatitude)
                        && !TextUtils.isEmpty(strlongitude))
                         {
                    // add the company to database
                    double latitude = Double.valueOf(strlatitude.toString());
                    double longitude = Double.valueOf(strlongitude.toString());
                    CountrieData createdCountry = mCountryCrud.createCountry(
                            countryName.toString(),
                            latitude,
                            longitude);

                    Log.d(TAG, "added company : " + createdCountry.getName());
                    Intent intent = new Intent();
                    intent.putExtra(ListCountriesActivity.EXTRA_ADDED_COUNTRY, (Serializable) createdCountry);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(this,"Country Created Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
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
        mCountryCrud.close();
    }
}

