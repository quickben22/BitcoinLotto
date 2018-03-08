package com.quickben22.bitcoinlotto;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by rista on 22-02-2018.
 */

public class SqliteClass {




    private static String TABLE_NAME = "bitcoin";
    private static String TABLE_NAME2 = "search";
    private static String ID_NAME = "_id";

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public SqliteClass(Context context,boolean update) {

        mDBHelper = new DataBaseHelper(context,update);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
    public  ArrayList<String> CheckIsDataAlreadyInDBorNot( ArrayList<String> fieldValues,ArrayList<String> addresses) {

        ArrayList<String> list= new ArrayList<>();
        String Query = "Select * from " + TABLE_NAME + " where";
        for (String v : fieldValues)
        {
            Query = Query + " "+ID_NAME+" = '" + v + "' or";
        }
        Query = Query.substring(0, Query.length() - 3);
//        String Query = "Select * from " + TABLE_NAME + " where " + ID_NAME + " = '" + fieldValue+"'";
        Cursor cursor = mDb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return list;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String address = cursor.getString(cursor.getColumnIndex(ID_NAME));
            address=addresses.get(fieldValues.indexOf(address));

            list.add(address);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public  boolean InsertSearchData( int a, String b, int c) {



//        String Query = "insert into "+TABLE_NAME2+" (combinations, last_a,time) values ("+a+",'" + b + "', " + c + ")";

        ContentValues initialValues = new ContentValues();
        initialValues.put("id", 1);
        initialValues.put("combinations", a);
        initialValues.put("last_a", b);
        initialValues.put("time", c);

        try {

            int id = (int) mDb.insertWithOnConflict(TABLE_NAME2, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (id == -1) {
                mDb.update(TABLE_NAME2, initialValues, "id=?", new String[] {"1"});  // number 1 is the _id here, update to variable for your code
            }
        }
        catch (Exception e)
        {
            return  false;

        }

        return  true;
    }


    public  ArrayList<String> GetSearchData() {

        ArrayList<String> list= new ArrayList<>();

        String Query = "Select * from " + TABLE_NAME2 + " where id = 1";


        try {
            Cursor cursor = mDb.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return list;
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String combinations = cursor.getString(cursor.getColumnIndex("combinations"));
                String last_a = cursor.getString(cursor.getColumnIndex("last_a"));
                String time = cursor.getString(cursor.getColumnIndex("time"));

                list.add(combinations);
                list.add(last_a);
                list.add(time);
                cursor.moveToNext();
            }

            cursor.close();
        }
        catch (Exception e)
        {


        }

        return  list;
    }

}












