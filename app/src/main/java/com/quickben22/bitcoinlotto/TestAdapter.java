package com.quickben22.bitcoinlotto;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

//    public TestAdapter createDatabase() throws SQLException
//    {
////        try
////        {
//////            mDbHelper.createDataBase();
////        }
////        catch (IOException mIOException)
////        {
////            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
////            throw new Error("UnableToCreateDatabase");
////        }
////        return this;
//    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }


    public  boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                String dbfield, String fieldValue) {


        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue+"'";
        Cursor cursor = mDb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int getTestData()
    {
        try
        {
            String sql ="SELECT * FROM bitcoin";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                int firstNameColIndex = mCur.getColumnIndex("_id");
                String fullName = mCur.getString(firstNameColIndex);
                mCur.moveToNext();
            }
            return mCur.getCount();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}