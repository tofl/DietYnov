package com.flitterman.dietynov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class MeasurementAdapter {

    private myDbHelper helper;

    public MeasurementAdapter(Context context) {
        helper = new myDbHelper(context);
    }

    public void insertData(String type, int value) {
        SQLiteDatabase database = helper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put(myDbHelper.TYPE, type);

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        contentValues.put(myDbHelper.DATE, date + "/" + month + "/" + year);

        contentValues.put(myDbHelper.VALUE, value);
        database.insert(myDbHelper.TABLE_MEASUREMENTS_NAME, null, contentValues);
    }

    public ArrayList<Measurement> getData(String type) {
        ArrayList<Measurement> list = new ArrayList<Measurement>();

        SQLiteDatabase db = helper.getWritableDatabase();
        //String[] columns = {"id", "type", "date", "value"};
        //Cursor cursor = db.query("measurements", columns, null, null, null, null, null, null);
        Cursor cursor = db.rawQuery("SELECT date, value FROM " + myDbHelper.TABLE_MEASUREMENTS_NAME + " WHERE type = '" + type + "'", null);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int value = cursor.getInt(cursor.getColumnIndex("value"));

            Log.e("HeyOh", type);
            Log.e("HeyOh", date);
            Log.e("HeyOh", String.valueOf(value));

            Measurement measurement = new Measurement();
            measurement.setType(type);
            measurement.setDate(date);
            measurement.setValue(value);

            list.add(measurement);
        }

        return list;
    }


    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "dietynov";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_MEASUREMENTS_NAME = "measurements";
        private static final String TYPE = "type";
        private static final String DATE = "date";
        private static final String VALUE = "value";

        private static final String CREATE_MEASUREMENTS_TABLE = "CREATE TABLE " + TABLE_MEASUREMENTS_NAME + " (" + TYPE + " varchar(30), " + DATE + " varchar(30), " + VALUE + " int);";
        private static final String DROP_MEASUREMENTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS_NAME;

        private Context context;

        public myDbHelper(Context ct) {
            super(ct, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = ct;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                Log.e("testtt", "table is being created");
                db.execSQL(CREATE_MEASUREMENTS_TABLE);
            } catch (Exception e) {
                Log.e("test", e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_MEASUREMENTS_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Log.e("exception", e.getMessage());
            }
        }

    }

}
