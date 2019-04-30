package com.flitterman.dietynov;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.util.Calendar;

public class settingsAdapter {

    myDbHelper myHelper;

    public settingsAdapter(Context context) {
        myHelper = new myDbHelper(context);
    }

    public void insertData(int size, String birthdate, String sex, int weight) {
        SQLiteDatabase database = myHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.SIZE, size);
        contentValues.put(myDbHelper.BIRTHDATE, birthdate);
        contentValues.put(myDbHelper.SEX, sex);
        database.insert(myDbHelper.TABLE_SETTINGS_NAME, null, contentValues);



        ContentValues contentWeightValues = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        contentWeightValues.put(myDbHelper.DATE, date + "/" + month + "/" + year);
        contentWeightValues.put(myDbHelper.WEIGHT, weight);
        database.insert(myDbHelper.TABLE_WEIGHT_NAME, null, contentWeightValues);
    }

    public boolean userExists() {
        SQLiteDatabase database = myHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(database, myDbHelper.TABLE_SETTINGS_NAME);

        if (count > 0) {
            Log.e("db", "user exists");
            return true;
        } else {
            Log.e("db", "user doesn't exist : " + count);
            return false;
        }
    }



    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "dietynov";
        private static final String TABLE_SETTINGS_NAME = "settings";
        private static final String TABLE_WEIGHT_NAME = "weight";
        private static final int DATABASE_VERSION = 1;
        private static final String SIZE = "size";
        private static final String BIRTHDATE = "birthdate";
        private static final String SEX = "sex";
        private static final String WEIGHT = "weight";
        private static final String ID = "id";
        private static final String DATE = "date";
        private static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS_NAME + " (" + SIZE + " integer, " + BIRTHDATE + " varchar(30), " + SEX + " VARCHAR(10));";
        private static final String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " VARCHAR(50), " + WEIGHT + " INTEGER);";
        private static final String DROP_SETTINGS_TABLE = "DROP TABLE IF EXISTS " + TABLE_SETTINGS_NAME;
        private static final String DROP_WEIGHT_TABLE = "DROP TABLE IF EXISTS " + TABLE_WEIGHT_NAME;

        private static final String TABLE_MEASUREMENTS_NAME = "measurements";
        private static final String TYPE = "type";
        private static final String VALUE = "value";

        private static final String CREATE_MEASUREMENTS_TABLE = "CREATE TABLE " + TABLE_MEASUREMENTS_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE + " varchar(30), " + DATE + " varchar(30), " + VALUE + " integer);";
        private static final String DROP_MEASUREMENTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS_NAME;

        private Context context;


        public myDbHelper(Context ct) {
            super(ct, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = ct;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_SETTINGS_TABLE);
                db.execSQL(CREATE_WEIGHT_TABLE);
                db.execSQL(CREATE_MEASUREMENTS_TABLE);
            } catch (Exception e) {
                Log.e("exception", e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_SETTINGS_TABLE);
                db.execSQL(DROP_WEIGHT_TABLE);
                db.execSQL(DROP_MEASUREMENTS_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Log.e("exception", e.getMessage());
            }
        }

    }

}
