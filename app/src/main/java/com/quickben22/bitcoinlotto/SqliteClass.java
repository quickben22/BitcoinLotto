package com.quickben22.bitcoinlotto;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



import java.io.IOException;

import java.util.ArrayList;

/**
 * Created by rista on 22-02-2018.
 */

public class SqliteClass {




    private static String TABLE_NAME = "bitcoin";
    private static String TABLE_NAME2 = "search";
    private static String TABLE_NAME3 = "adrese";
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

    public  boolean InsertAddressData( String a,String b) {



        ContentValues initialValues = new ContentValues();
        initialValues.put("start", a);
        initialValues.put("end", b);

        try {

            int id = (int) mDb.insertWithOnConflict(TABLE_NAME3, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (id == -1) {
                mDb.update(TABLE_NAME3, initialValues, "start=?", new String[]{a});  // number 1 is the _id here, update to variable for your code

            }
        }
        catch (Exception e)
        {
            return  false;

        }

        return  true;
    }




    public  String[] CheckIfAlreadyChecked(String a)
    {
        String[] povrat={a,""};

        String Query = "Select * from " + TABLE_NAME3 ;


        try {
            Cursor cursor = mDb.rawQuery(Query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return povrat;
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String start = cursor.getString(cursor.getColumnIndex("start"));
                String end = cursor.getString(cursor.getColumnIndex("end"));

                if((a.compareTo(start)==1 || a.compareTo(start)==0) && (a.compareTo(end)==-1 || a.compareTo(end)==0)) {
                    povrat[0]=start;
                    povrat[1]=end;
                    cursor.close();
                    return povrat;
                }
                cursor.moveToNext();
            }

            cursor.close();
        }
        catch (Exception e)
        {


        }

        return  povrat;
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












