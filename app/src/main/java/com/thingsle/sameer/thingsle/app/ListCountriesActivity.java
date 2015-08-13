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
import com.thingsle.sameer.thingsle.adapter.ListCountriesAdapter;
import com.thingsle.sameer.thingsle.data.CountrieData;
import com.thingsle.sameer.thingsle.data.CountryCRUD;

/**
 * Created by Sameer on 8/13/2015.
 */
public class ListCountriesActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListCountriesActivity";

    public static final int REQUEST_CODE_ADD_COUNTRY = 40;
    public static final String EXTRA_ADDED_COUNTRY = "extra_key_added_company";

    private ListView mListviewCountries;
    private TextView mTxtEmptyListCountries;
    private ImageButton mBtnAddCountry;

    private ListCountriesAdapter mAdapter;
    private List<CountrieData> mListCountries;
    private CountryCRUD mCountryCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_countries);

        // initialize views
        initViews();

        // fill the listView
        mCountryCrud = new CountryCRUD(this);
        mListCountries = mCountryCrud.getAllCountries();
        if(mListCountries != null && !mListCountries.isEmpty()) {
            mAdapter = new ListCountriesAdapter(this, mListCountries);
            mListviewCountries.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListCountries.setVisibility(View.VISIBLE);
            mListviewCountries.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListviewCountries = (ListView) findViewById(R.id.list_countries);
        this.mTxtEmptyListCountries = (TextView) findViewById(R.id.txt_empty_list_countries);
        this.mBtnAddCountry = (ImageButton) findViewById(R.id.btn_add_country);
        this.mListviewCountries.setOnItemClickListener(this);
        this.mListviewCountries.setOnItemLongClickListener(this);
        this.mBtnAddCountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_country:
                Intent intent = new Intent(this, AddCountry.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_COUNTRY);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_COUNTRY) {
            if(resultCode == RESULT_OK) {
                // add the added country to the listCountry and refresh the listView
                if(data != null) {
                    CountrieData createdCountry = (CountrieData) data.getSerializableExtra(EXTRA_ADDED_COUNTRY);
                    if(createdCountry != null) {
                        if(mListCountries == null)
                            mListCountries = new ArrayList<CountrieData>();
                        mListCountries.add(createdCountry);

                        if(mListviewCountries.getVisibility() != View.VISIBLE) {
                            mListviewCountries.setVisibility(View.VISIBLE);
                            mTxtEmptyListCountries.setVisibility(View.GONE);
                        }

                        if(mAdapter == null) {
                            mAdapter = new ListCountriesAdapter(this, mListCountries);
                            mListviewCountries.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListCountries);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountryCrud.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CountrieData clickedCountry = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedCountry.getName());
       /* Intent intent = new Intent(this, ListCitiesActivity.class);
        intent.putExtra(ListCitiesActivity.EXTRA_SELECTED_COUNTRY_ID, clickedCountry.getId());
        startActivity(intent);*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CountrieData clickedCountry = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+clickedCountry.getName());
        showDeleteDialogConfirmation(clickedCountry);
        return true;
    }

    private void showDeleteDialogConfirmation(final CountrieData clickedCountry) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the \""+clickedCountry.getName()+"\" country ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                if(mCountryCrud != null) {
                    mCountryCrud.deleteCountry(clickedCountry);
                    mListCountries.remove(clickedCountry);

                    //refresh the listView
                    if(mListCountries.isEmpty()) {
                        mListCountries = null;
                        mListviewCountries.setVisibility(View.GONE);
                        mTxtEmptyListCountries.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListCountries);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListCountriesActivity.this, "Country Deleted Successfully", Toast.LENGTH_SHORT).show();
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
