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
public class CityCRUD {
    public static final String TAG = "CityCRUD";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHandler mDatabaseHandler;
    private String[] mAllColumns = { DatabaseHandler.CITY_COLUMN_ID,
            DatabaseHandler.CITY_COLUMN_NAME,
            DatabaseHandler.CITY_COLUMN_LONGITUDE,
            DatabaseHandler.CITY_COLUMN_LATITUDE,
            DatabaseHandler.CITY_COLUMN_THINGS_TO_DO,
            DatabaseHandler.CITY_COLUMN_RATING};

    public CityCRUD(Context context) {
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

    public CitiesData createCity(String name, double longi, double lat, String rating, String thingsTO,long countryId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.CITY_COLUMN_NAME, name);
        values.put(DatabaseHandler.CITY_COLUMN_LONGITUDE, longi);
        values.put(DatabaseHandler.CITY_COLUMN_LATITUDE, lat);
        values.put(DatabaseHandler.CITY_COLUMN_RATING, rating);
        values.put(DatabaseHandler.CITY_COLUMN_THINGS_TO_DO, thingsTO);
        values.put(DatabaseHandler.CITY_COLUMN_COUNTRY_ID, thingsTO);
        long insertId = mDatabase.insert(DatabaseHandler.CITY_TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DatabaseHandler.CITY_TABLE_NAME,
                mAllColumns, DatabaseHandler.CITY_COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        CitiesData citiesData = cursorToCity(cursor);
        cursor.close();
        return citiesData;
    }

    public void deleteCity(CitiesData citiesData) {
        long id = citiesData.getCity_id();


        // delete all places of this country

        PlacesCRUD placesCRUD = new PlacesCRUD(mContext);
        List<PlacesData> listplaces = placesCRUD.getPlacesOfCity(id);
        if (listplaces != null && !listplaces.isEmpty()) {
            for (PlacesData e : listplaces) {
                placesCRUD.deletePlace(e);
            }
        }
        System.out.println("the deleted City has the id: " + id);
        mDatabase.delete(DatabaseHandler.CITY_TABLE_NAME, DatabaseHandler.CITY_COLUMN_ID + " = " + id, null);
    }

    public List<CitiesData> getAllCities() {
        List<CitiesData> listCitites = new ArrayList<CitiesData>();

        Cursor cursor = mDatabase.query(DatabaseHandler.CITY_TABLE_NAME,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CitiesData citiesData = cursorToCity(cursor);
            listCitites.add(citiesData);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listCitites;
    }


    public CitiesData getCityById(long id) {
        Cursor cursor = mDatabase.query(DatabaseHandler.CITY_TABLE_NAME, mAllColumns,
                DatabaseHandler.CITY_COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        CitiesData city = cursorToCity(cursor);
        return city;
    }
    public List<CitiesData> getCitiesOfCountry(long cityId) {
        List<CitiesData> listCities = new ArrayList<CitiesData>();

        Cursor cursor = mDatabase.query(DatabaseHandler.CITY_TABLE_NAME, mAllColumns
                , DatabaseHandler.CITY_COLUMN_ID + " = ?",
                new String[] { String.valueOf(cityId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CitiesData citiesData = cursorToCity(cursor);
            listCities.add(citiesData);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listCities;
    }

    private CitiesData cursorToCity(Cursor cursor) {
        CitiesData citiesData = new CitiesData();
        citiesData.setCity_id(cursor.getInt(0));
        citiesData.setName(cursor.getString(1));
        citiesData.setLongi(cursor.getFloat(2));
        citiesData.setLat(cursor.getFloat(3));
        citiesData.setRating(cursor.getString(4));
        citiesData.setThingsTO(cursor.getString(5));

        return citiesData;

    }

}


