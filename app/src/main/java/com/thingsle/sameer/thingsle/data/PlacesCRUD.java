package com.thingsle.sameer.thingsle.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 8/12/2015.
 */
public class PlacesCRUD {

    public static final String TAG = "EmployeeDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHandler mDatabaseHandler;
    private String[] mAllColumns = {
            DatabaseHandler.PLACE_COLUMN_ID,
            DatabaseHandler.PLACE_COLUMN_NAME,
            DatabaseHandler.PLACE_COLUMN_LONGITUDE,
            DatabaseHandler.PLACE_COLUMN_LATITUDE,
            DatabaseHandler.PLACE_COLUMN_RATING,
            DatabaseHandler.PLACE_COLUMN_DESCRIPTION,
            DatabaseHandler.PLACE_COLUMN_DETAILS,
            DatabaseHandler.PLACE_COLUMN_STATUS,
            DatabaseHandler.PLACE_COLUMN_CITY_ID};

    public PlacesCRUD(Context context) {
        mDatabaseHandler = new DatabaseHandler(context);
        this.mContext = context;
        // open the database
        try {
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHandler.getWritableDatabase();
    }

    public void close() {
        mDatabaseHandler.close();
    }

    public PlacesData createEmploye(String name, double longi, double lat, String rating, String description, String details, Boolean status, int city_id) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.PLACE_COLUMN_NAME, name);
        values.put(DatabaseHandler.PLACE_COLUMN_LONGITUDE, longi);
        values.put(DatabaseHandler.PLACE_COLUMN_LATITUDE, lat);
        values.put(DatabaseHandler.PLACE_COLUMN_RATING, rating);
        values.put(DatabaseHandler.PLACE_COLUMN_DESCRIPTION, description);
        values.put(DatabaseHandler.PLACE_COLUMN_DETAILS, details);
        values.put(DatabaseHandler.PLACE_COLUMN_STATUS, status);
        long insertId = mDatabase.insert(DatabaseHandler.PLACE_TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DatabaseHandler.PLACE_TABLE_NAME,
                mAllColumns, DatabaseHandler.CITY_COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        PlacesData newPlaceData = cursorToPlaces(cursor);
        cursor.close();
        return newPlaceData;
    }

    public void deletePlace(PlacesData placesData) {
        long id = placesData.getPlace_id();
        System.out.println("the deleted place has the id: " + id);
        mDatabase.delete(DatabaseHandler.PLACE_TABLE_NAME, DatabaseHandler.PLACE_COLUMN_ID + " = " + id, null);
    }

    public List<PlacesData> getAllEmployees() {
        List<PlacesData> listPlaces = new ArrayList<PlacesData>();

        Cursor cursor = mDatabase.query(DatabaseHandler.PLACE_TABLE_NAME,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlacesData placesData = cursorToPlaces(cursor);
            listPlaces.add(placesData);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPlaces;
    }

    public List<PlacesData> getPlacesOfCity(long cityId) {
        List<PlacesData> listPlaces = new ArrayList<PlacesData>();

        Cursor cursor = mDatabase.query(DatabaseHandler.PLACE_TABLE_NAME, mAllColumns
                , DatabaseHandler.PLACE_COLUMN_CITY_ID + " = ?",
                new String[] { String.valueOf(cityId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlacesData placesData = cursorToPlaces(cursor);
            listPlaces.add(placesData);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPlaces;
    }

    private PlacesData cursorToPlaces(Cursor cursor) {
        PlacesData placesData = new PlacesData();
        placesData.setPlace_id(cursor.getInt(0));
        placesData.setName(cursor.getString(1));
        placesData.setLongi(cursor.getDouble(2));
        placesData.setLat(cursor.getDouble(3));
        placesData.setRating(cursor.getString(4));
        placesData.setDescription(cursor.getString(5));
        placesData.setDetails(cursor.getString(6));

        // get The city by id
        int cityId = cursor.getInt(7);
        CityCRUD cityCRUD = new CityCRUD(mContext);
        CitiesData citiesData = cityCRUD.getCityById(cityId);
        if(citiesData != null)
            placesData.setCitiesData(citiesData);

        return placesData;
    }

}

