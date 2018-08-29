/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sunshine.data.WeatherContract.WeatherEntry;

/**
 * Manages a local database for weather data.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";

    public static final int DATABASE_VERSION = 3;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +

                        WeatherContract.WeatherEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WeatherContract.WeatherEntry.COLUMN_DATE        + " INTEGER NOT NULL UNIQUE, " +
                        WeatherContract.WeatherEntry.COLUMN_DEGREES     + " REAL NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_MAX_TEMP    + " REAL NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_MIN_TEMP    + " REAL NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_WEATHER_ID     + " INTEGER NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_HUMIDITY    +  " REAL NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_PRESSURE    + " REAL NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_WIND_SPEED     + " REAL NOT NULL" + ");";

        db.execSQL(SQL_CREATE_WEATHER_TABLE);

        /* db.execSQL("create table " + DATABASE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_DATE INTEGER,  +
                "", +
                "", +
                "" +
                "" +
                " )");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        onCreate(db);
    }
        // TODO (3) Within onUpgrade, drop the weather table if it exists
        // TODO (4) call onCreate and pass in the SQLiteDatabase (passed in to onUpgrade)

}