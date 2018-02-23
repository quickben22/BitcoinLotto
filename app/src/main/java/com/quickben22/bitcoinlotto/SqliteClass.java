package com.quickben22.bitcoinlotto;



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
}












