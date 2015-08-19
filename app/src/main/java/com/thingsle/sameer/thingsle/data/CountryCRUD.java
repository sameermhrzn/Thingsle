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
public class CountryCRUD {


    public static final String TAG = "CountryCRUD";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHandler mDatabaseHandler;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHandler.COUNTRY_COLUMN_ID,
            DatabaseHandler.COUNTRY_COLUMN_NAME, DatabaseHandler.COUNTRY_COLUMN_LATITUDE,
            DatabaseHandler.COUNTRY_COLUMN_LONGITUDE};
            //DatabaseHandler.COUNTRY_COLUMN_CITY_ID};

    public CountryCRUD(Context context) {
        this.mContext = context;
        mDatabaseHandler = new DatabaseHandler(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
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

    public CountrieData createCountry( String name, double lat, double longi) {
        ContentValues values = new ContentValues();
        //values.put(DatabaseHandler.COUNTRY_COLUMN_NAME, id);
        values.put(DatabaseHandler.COUNTRY_COLUMN_NAME, name);
        values.put(DatabaseHandler.COUNTRY_COLUMN_LATITUDE, lat);
        values.put(DatabaseHandler.CITY_COLUMN_LONGITUDE, longi);

        long insertId = mDatabase.insert(DatabaseHandler.COUNTRY_TABLE_NAME, null, values);

        Cursor cursor = mDatabase.query(DatabaseHandler.COUNTRY_TABLE_NAME, mAllColumns,
                DatabaseHandler.COUNTRY_COLUMN_ID + " = " + insertId, null, null,
                null,null);
        cursor.moveToFirst();
        CountrieData newCountry = cursorToCountry(cursor);
        cursor.close();
        return newCountry;
    }

       /* Cursor cursor = mDatabase.query(DatabaseHandler.COUNTRY_TABLE_NAME, mAllColumns,
                DatabaseHandler.COUNTRY_COLUMN_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        CountrieData newCountry = cursorToCountry(cursor);
        cursor.close();
        return newCountry;*/

    public void deleteCountry(CountrieData countrieData) {
        long id = countrieData.getId();

        // delete all cities of this country

        CityCRUD cityCRUD = new CityCRUD(mContext);
        List<CitiesData> listCities = cityCRUD.getCitiesOfCountry(id);
        if (listCities != null && !listCities.isEmpty()) {
            for (CitiesData e : listCities) {
                cityCRUD.deleteCity(e);
            }
        }

        System.out.println("the deleted Country has the id: " + id);
        mDatabase.delete(DatabaseHandler.COUNTRY_TABLE_NAME, DatabaseHandler.COUNTRY_COLUMN_ID
                + " = " + id, null);
    }

    public List<CountrieData> getAllCountries() {
        List<CountrieData> listCountries = new ArrayList<CountrieData>();

        Cursor cursor = mDatabase.query(DatabaseHandler.COUNTRY_TABLE_NAME, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CountrieData country = cursorToCountry(cursor);
                listCountries.add(country);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCountries;
    }

    public CountrieData getCountryById(long id) {
        Cursor cursor = mDatabase.query(DatabaseHandler.COUNTRY_TABLE_NAME, mAllColumns,
                DatabaseHandler.COUNTRY_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        CountrieData countrieData = cursorToCountry(cursor);
        return countrieData;
    }

    protected CountrieData cursorToCountry(Cursor cursor) {
        CountrieData country = new CountrieData();
        country.setId(cursor.getLong(0));
        country.setName(cursor.getString(1));
        country.setLat(cursor.getDouble(2));
        country.setLongi(cursor.getDouble(3));
        return country;
    }

}

