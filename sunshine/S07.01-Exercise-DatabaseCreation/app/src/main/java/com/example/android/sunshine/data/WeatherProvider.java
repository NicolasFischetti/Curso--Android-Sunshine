package com.example.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.sunshine.R;

import java.util.ArrayList;

public class WeatherProvider extends ContentProvider{


    private WeatherDbHelper mOpenHelper;
    private static final int CODE_WEATHER = 0;
    private static final int CODE_WEATHER_WITH_DATE = 1;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    
    public static UriMatcher buildUriMatcher() {
        final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authentication = WeatherContract.CONTENT_AUTHORITY;
        return mUriMatcher;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        
        
    }
    
    @Override
    public boolean onCreate() {

         mOpenHelper = new WeatherDbHelper(getContext());
         
        return true;
        
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }
    

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
    
    /* (1) Implement the bulkInsert method
  (2) Only perform our implementation of bulkInsert if the URI matches the CODE_WEATHER code

//              (3) Return the number of rows inserted from our implementation of bulkInsert

//          (4) If the URI does match match CODE_WEATHER, return the super implementation of bulkInsert */
    
}

