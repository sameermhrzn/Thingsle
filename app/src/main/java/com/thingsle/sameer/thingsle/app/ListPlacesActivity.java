package com.thingsle.sameer.thingsle.app;

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
import com.thingsle.sameer.thingsle.adapter.ListPlacesAdapter;
import com.thingsle.sameer.thingsle.data.CitiesData;
import com.thingsle.sameer.thingsle.data.CityCRUD;
import com.thingsle.sameer.thingsle.data.PlacesCRUD;
import com.thingsle.sameer.thingsle.data.PlacesData;

import java.util.ArrayList;
import java.util.List;

public class ListPlacesActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListPlacesActivity";

    public static final int REQUEST_CODE_ADD_PLACE = 40;
    public static final String EXTRA_ADDED_PLACE = "extra_key_added_place";
    public static final String EXTRA_SELECTED_CITY_ID = "extra_key_selected_city_id";

    private ListView mListViewPlaces;
    private TextView mTxtEmptyListPlaces;
    private ImageButton mBtnAddPlaces;

    private ListPlacesAdapter mAdapter;
    private List<PlacesData> mListPlaces;
    private PlacesCRUD mPlacesCrud;

    private long mCityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_places);

        // initialize views
        initViews();

        // get the city id from extras
        mPlacesCrud = new PlacesCRUD(this);
        Intent intent  = getIntent();
        if(intent != null) {
            this.mCityId = intent.getLongExtra(EXTRA_SELECTED_CITY_ID, -1);
        }

        if(mCityId != -1) {
            mListPlaces = mPlacesCrud.getPlacesOfCity(mCityId);
            // fill the listView
            if(mListPlaces != null && !mListPlaces.isEmpty()) {
                mAdapter = new ListPlacesAdapter(this, mListPlaces);
                mListViewPlaces.setAdapter(mAdapter);
            }
            else {
                mTxtEmptyListPlaces.setVisibility(View.VISIBLE);
                mListViewPlaces.setVisibility(View.GONE);
            }
        }
    }

    private void initViews() {
        this.mListViewPlaces = (ListView) findViewById(R.id.list_cities);
        this.mTxtEmptyListPlaces = (TextView) findViewById(R.id.txt_empty_list_places);
        this.mBtnAddPlaces = (ImageButton) findViewById(R.id.btn_add_places);
        this.mListViewPlaces.setOnItemClickListener(this);
        this.mListViewPlaces.setOnItemLongClickListener(this);
        this.mBtnAddPlaces.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_places:
                Intent intent = new Intent(this, AddPlaces.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PLACE);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_PLACE) {
            if(resultCode == RESULT_OK) {
                //refresh the listView
                if(mListPlaces == null || !mListPlaces.isEmpty()) {
                    mListPlaces = new ArrayList<PlacesData>();
                }

                if(mPlacesCrud == null)
                    mPlacesCrud = new PlacesCRUD(this);
                mListPlaces = mPlacesCrud.getPlacesOfCity(mCityId);

                if(mListPlaces != null && !mListPlaces.isEmpty() &&
                        mListViewPlaces.getVisibility() != View.VISIBLE) {
                    mTxtEmptyListPlaces.setVisibility(View.GONE);
                    mListViewPlaces.setVisibility(View.VISIBLE);
                }

                if(mAdapter == null) {
                    mAdapter = new ListPlacesAdapter(this, mListPlaces);
                    mListViewPlaces.setAdapter(mAdapter);
                }
                else {
                    mAdapter.setItems(mListPlaces);
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
        mPlacesCrud.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlacesData clickedPlaces = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedPlaces.getName());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        PlacesData clickedPlaces = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+clickedPlaces.getName());

        showDeleteDialogConfirmation(clickedPlaces);
        return true;
    }

    private void showDeleteDialogConfirmation(final PlacesData places) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the Places \""
                        + places.getName());

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the employee and refresh the list
                if (mPlacesCrud != null) {
                    mPlacesCrud.deletePlace(places);

                    //refresh the listView
                    mListPlaces.remove(places);
                    if (mListPlaces.isEmpty()) {
                        mListViewPlaces.setVisibility(View.GONE);
                        mTxtEmptyListPlaces.setVisibility(View.VISIBLE);
                    }

                    mAdapter.setItems(mListPlaces);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListPlacesActivity.this, "Places deleted successfully", Toast.LENGTH_SHORT).show();

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
