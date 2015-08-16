package com.thingsle.sameer.thingsle.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sameer on 8/10/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    public static final String TAG = "Database Handler";

    public static final String DATABASE_NAME = "ThingsleDb.db";
    private static final int DATABASE_VERSION = 1;

    //columns for country table
    public static final String COUNTRY_TABLE_NAME = "country";
    public static final String COUNTRY_COLUMN_ID = "country_id";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_LATITUDE = "lat";
    public static final String COUNTRY_COLUMN_LONGITUDE = "longi";
    public static final String COUNTRY_COLUMN_CITY_ID = "city_id";

    //columns for city table
    public static final String CITY_TABLE_NAME = "city";
    public static final String CITY_COLUMN_ID = "city_id";
    public static final String CITY_COLUMN_COUNTRY_ID = "country_id";
    public static final String CITY_COLUMN_PLACE_ID = "place_id";
    public static final String CITY_COLUMN_NAME = "name";
    public static final String CITY_COLUMN_LATITUDE = "lat";
    public static final String CITY_COLUMN_LONGITUDE = "longi";
    public static final String CITY_COLUMN_RATING = "rating";
    public static final String CITY_COLUMN_THINGS_TO_DO = "things_to_do";


    //columns for places table
    public static final String PLACE_TABLE_NAME = "place";
    public static final String PLACE_COLUMN_ID = "place_id";
    public static final String PLACE_COLUMN_CITY_ID = "city_id";
    public static final String PLACE_COLUMN_NAME = "name";
    public static final String PLACE_COLUMN_LATITUDE = "lat";
    public static final String PLACE_COLUMN_LONGITUDE = "longi";
    public static final String PLACE_COLUMN_RATING = "rating";
    public static final String PLACE_COLUMN_STATUS = "status";
    public static final String PLACE_COLUMN_DETAILS = "details";
    public static final String PLACE_COLUMN_DESCRIPTION = "description";

    // SQL statement of the country table creation
    private static final String SQL_CREATE_TABLE_COUNTRY = "CREATE TABLE " + COUNTRY_TABLE_NAME + " ( "
            + COUNTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COUNTRY_COLUMN_NAME + " TEXT NOT NULL, "
            + COUNTRY_COLUMN_LATITUDE + " REAL NOT NULL, "
            + COUNTRY_COLUMN_LONGITUDE + " REAL NOT NULL, "
            + COUNTRY_COLUMN_CITY_ID + " INTEGER NOT NULL)";

    // SQL statement of the city table creation
    private static final String SQL_CREATE_TABLE_CITY = "CREATE TABLE " + CITY_TABLE_NAME + " ( "
            + CITY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CITY_COLUMN_NAME + " TEXT NOT NULL, "
            + CITY_COLUMN_LONGITUDE + " REAL NOT NULL, "
            + CITY_COLUMN_LATITUDE + " REAL NOT NULL, "
            + CITY_COLUMN_RATING + " TEXT NOT NULL, "
            + CITY_COLUMN_THINGS_TO_DO + " TEXT NOT NULL,"
            + CITY_COLUMN_PLACE_ID + " INTEGER NOT NULL)";


    // SQL statement of the places table creation
    private static final String SQL_CREATE_TABLE_PLACES = "CREATE TABLE " + PLACE_TABLE_NAME + " ( "
            + PLACE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLACE_COLUMN_NAME + " TEXT NOT NULL, "
            + PLACE_COLUMN_LONGITUDE + " REAL NOT NULL, "
            + PLACE_COLUMN_LATITUDE + " REAL NOT NULL, "
            + PLACE_COLUMN_RATING + " TEXT NOT NULL, "
            + PLACE_COLUMN_DESCRIPTION + " TEXT NOT NULL, "
            + PLACE_COLUMN_DETAILS + " TEXT NOT NULL, "
            + PLACE_COLUMN_STATUS + " BOOLEAN,"
            + PLACE_COLUMN_CITY_ID + " INTEGER NOT NULL)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO Auto-generated method stub
        database.execSQL(SQL_CREATE_TABLE_COUNTRY);
        database.execSQL(SQL_CREATE_TABLE_CITY);
        database.execSQL(SQL_CREATE_TABLE_PLACES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COUNTRY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLACE_TABLE_NAME);


        // recreate the tables
        onCreate(db);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
