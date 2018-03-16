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

    public SqliteClass(Context context) {

        mDBHelper = new DataBaseHelper(context);

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

    public  void update(boolean b)
    {
        mDBHelper.setmNeedUpdate(b);
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

    public  boolean InsertSearchData( int a, String b, int c,String d,String e2,String f,String g, String h, String i, String j, String k, String k2, String k3) {



//        String Query = "insert into "+TABLE_NAME2+" (combinations, last_a,time) values ("+a+",'" + b + "', " + c + ")";

        ContentValues initialValues = new ContentValues();
        initialValues.put("id", 1);
        initialValues.put("combinations", a);
        initialValues.put("last_a", b);
        initialValues.put("time", c);
        initialValues.put("solution1", d);
        initialValues.put("solution2", e2);
        initialValues.put("solution3", f);
        initialValues.put("solution4", g);
        initialValues.put("solution5", h);
        initialValues.put("solution6", i);
        initialValues.put("solution7", j);
        initialValues.put("solution8", k);
        initialValues.put("solution9", k2);
        initialValues.put("solution10", k3);
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
        CryptoClass.keysD.setEndAddress(b);
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

                if(a.toLowerCase().compareTo(start.toLowerCase())>=0  && a.toLowerCase().compareTo(end.toLowerCase())<=0 )
                {
                    if(povrat[1].toLowerCase().compareTo(end.toLowerCase())<0)
                    {
                        povrat[0] = start;
                        povrat[1] = end;
                    }


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
                String s1 = cursor.getString(cursor.getColumnIndex("solution1"));
                String s2 = cursor.getString(cursor.getColumnIndex("solution2"));
                String s3 = cursor.getString(cursor.getColumnIndex("solution3"));
                String s4 = cursor.getString(cursor.getColumnIndex("solution4"));
                String s5 = cursor.getString(cursor.getColumnIndex("solution5"));
                String s6 = cursor.getString(cursor.getColumnIndex("solution6"));
                String s7 = cursor.getString(cursor.getColumnIndex("solution7"));
                String s8 = cursor.getString(cursor.getColumnIndex("solution8"));
                String s9 = cursor.getString(cursor.getColumnIndex("solution9"));
                String s10 = cursor.getString(cursor.getColumnIndex("solution10"));
                list.add(combinations);
                list.add(last_a);
                list.add(time);
                list.add(s1);
                list.add(s2);
                list.add(s3);
                list.add(s4);
                list.add(s5);
                list.add(s6);
                list.add(s7);
                list.add(s8);
                list.add(s9);
                list.add(s10);
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












