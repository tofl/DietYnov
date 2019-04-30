package com.flitterman.dietynov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class weightAdapter {

    MyDbHelper helper;

    public weightAdapter(Context ct) {
        helper = new MyDbHelper(ct);
    }

    public ArrayList<Weight> getData() {

        ArrayList<Weight> weightList = new ArrayList<Weight>();

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {"id", "date", "weight"};
        Cursor cursor = db.query("weight", columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int weightVal = cursor.getInt(cursor.getColumnIndex("weight"));

            Weight weight = new Weight();
            weight.setId(id);
            weight.setDate(date);
            weight.setWeight(weightVal);

            weightList.add(weight);
        }

        return weightList;
    }

    public void insertData(int weight) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentWeightValues = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        contentWeightValues.put(MyDbHelper.DATE, date + "/" + month + "/" + year);
        contentWeightValues.put(MyDbHelper.WEIGHT, weight);
        database.insert(MyDbHelper.TABLE_WEIGHT_NAME, null, contentWeightValues);
    }


    static class MyDbHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "dietynov";
        private static final String WEIGHT = "weight";
        private static final String TABLE_WEIGHT_NAME = "weight";
        private static final String DATE = "date";

        private Context context;

        public MyDbHelper(Context ct) {
            super(ct, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = ct;
        }

        public void onCreate(SQLiteDatabase db) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    }

}
