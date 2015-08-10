package com.thingsle.sameer.thingsle.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sameer on 8/10/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private HashMap hp;

    public static final String DATABASE_NAME = "ThingsleDb.db";

    public static final String COUNTRY_TABLE_NAME = "country";
    public static final String COUNTRY_COLUMN_ID = "country_id";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_LATITUDE = "lat";
    public static final String COUNTRY_COLUMN_LONGITUDE = "longi";
    public static final String COUNTRY_COLUMN_CITY_ID = "city_id";

    public static final String CITY_TABLE_NAME = "city";
    public static final String CITY_COLUMN_ID = "city_id";
    public static final String CITY_COLUMN_PLACE_ID = "place_id";
    public static final String CITY_COLUMN_NAME = "name";
    public static final String CITY_COLUMN_LATITUDE = "lat";
    public static final String CITY_COLUMN_LONGITUDE = "longi";
    public static final String CITY_COLUMN_RATING = "rating";
    public static final String CITY_COLUMN_THINGS_TO_DO="things_to_do";


    public static final String PLACE_TABLE_NAME = "place";
    public static final String PLACE_COLUMN_ID = "place_id";
    public static final String PLACE_COLUMN_CITY_ID = "city_id";
    public static final String PLACE_COLUMN_NAME = "name";
    public static final String PLACE_COLUMN_LATITUDE = "lat";
    public static final String PLACE_COLUMN_LONGITUDE = "longi";
    public static final String PLACE_COLUMN_RATING = "rating";
    public static final String PLACE_COLUMN_STATUS="status";
    public static final String PLACE_COLUMN_DETAILS="details";
    public static final String PLACE_COLUMN_DESCRIPTION="description";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + COUNTRY_TABLE_NAME+
                        "("+ COUNTRY_COLUMN_ID + "integer primary key,"
                        +COUNTRY_COLUMN_NAME +"text,"
                        + COUNTRY_COLUMN_LONGITUDE + "float,"
                        + COUNTRY_COLUMN_LATITUDE + "float,"
                        +COUNTRY_COLUMN_CITY_ID + "integer foreign key)"
        );
        db.execSQL(
                "create table " + CITY_TABLE_NAME+
                        "("+ CITY_COLUMN_ID + "integer primary key,"
                        +CITY_COLUMN_NAME +"text,"
                        + CITY_COLUMN_LONGITUDE+ "float,"
                        + CITY_COLUMN_LATITUDE + "float,"
                        +CITY_COLUMN_PLACE_ID + "integer foreign key"
                        +CITY_COLUMN_RATING +"text,"
                        +CITY_COLUMN_THINGS_TO_DO +"text"+
                        ")"
        );
        db.execSQL(
                "create table " + PLACE_TABLE_NAME+
                        "("+ PLACE_COLUMN_ID + "integer primary key,"
                        +PLACE_COLUMN_NAME +"text,"
                        + PLACE_COLUMN_LONGITUDE+ "float,"
                        +PLACE_COLUMN_LATITUDE + "float,"
                        +PLACE_COLUMN_CITY_ID + "integer foreign key"
                        +PLACE_COLUMN_RATING +"text,"
                        +PLACE_COLUMN_DESCRIPTION +"text,"
                        +PLACE_COLUMN_DETAILS +"text,"
                        +PLACE_COLUMN_STATUS +"boolean"+
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertCountry  (String name,  Float lat, Float longi,Integer city_id )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lat", lat);
        contentValues.put("long",longi);
        contentValues.put("city_id", city_id);
        db.insert("country", null, contentValues);
        return true;
    }
    public boolean insertCity  (Integer city_id,String name,  Float lat, Float longi,Integer place_id, String rating,String thingsTO)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_id", city_id);
        contentValues.put("place_id", place_id);
        contentValues.put("name", name);
        contentValues.put("lat", lat);
        contentValues.put("long",longi);
        contentValues.put("rating",rating);
        contentValues.put("things_to_do",thingsTO);
        db.insert("city", null, contentValues);
        return true;
    }

    public boolean insertPlace  (Integer city_id,String name,  Float lat, Float longi,Integer place_id, String rating,String details,String description, Boolean status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_id", city_id);
        contentValues.put("place_id", place_id);
        contentValues.put("name", name);
        contentValues.put("lat", lat);
        contentValues.put("long",longi);
        contentValues.put("rating",rating);
        contentValues.put("details",details);
        contentValues.put("description",description);
        contentValues.put("status",status);
        db.insert("city", null, contentValues);
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from country where "+COUNTRY_COLUMN_ID+"="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, COUNTRY_TABLE_NAME);
        return numRows;
    }

    public boolean updateCountry (Integer country_id, String name,  Float lat, Float longi,Integer city_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("longi", longi);
        contentValues.put("city_id", city_id);
        contentValues.put("lat", lat);
        db.update("country", contentValues, "country_id = ? ", new String[] { Integer.toString(country_id) } );
        return true;
    }

    public Integer deleteCountry (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("country",
                "country_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCountry()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from country", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COUNTRY_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}