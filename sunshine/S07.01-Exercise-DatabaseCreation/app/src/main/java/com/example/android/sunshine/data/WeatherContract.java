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

import android.net.Uri;
import android.provider.BaseColumns;
import android.renderscript.Sampler;

import com.example.android.sunshine.utilities.SunshineDateUtils;

/**
 * Defines table and column names for the weather database. This class is not necessary, but keeps
 * the code organized.
 */
public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    /*
     * Possible paths that can be appended to BASE_CONTENT_URI to form valid URI's that Sunshine
     * can handle. For instance,
     *
     *     content://com.example.android.sunshine/weather/
     *     [           BASE_CONTENT_URI         ][ PATH_WEATHER ]
     *
     * is a valid path for looking at weather data.
     *
     *      content://com.example.android.sunshine/givemeroot/
     *
     * will fail, as the ContentProvider hasn't been given any information on what to do with
     * "givemeroot". At least, let's hope not. Don't be that dev, reader. Don't be that dev.
     */
    public static final String PATH_WEATHER = "weather";

    /* Inner class that defines the table contents of the weather table */
    //  TODO (1) Within WeatherContract, create a public static final class called WeatherEntry that implements BaseColumns
    public static final class WeatherEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEATHER = "weather_id";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEEDLE = "wind";
        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }

        public static String getSqlSelectForTodayOnwards() {
            long normalizedUtcNow = SunshineDateUtils.normalizeDate(System.currentTimeMillis());
            return WeatherContract.WeatherEntry.COLUMN_DATE + " >= " + normalizedUtcNow;
        }

//      Do steps 2 through 10 within the WeatherEntry class

//      TODO (2) Create a public static final String call TABLE_NAME with the value "weather"

//      TODO (3) Create a public static final String call COLUMN_DATE with the value "date"

//      TODO (4) Create a public static final String call COLUMN_WEATHER_ID with the value "weather_id"

//      TODO (5) Create a public static final String call COLUMN_MIN_TEMP with the value "min"
//      TODO (6) Create a public static final String call COLUMN_MAX_TEMP with the value "max"

//      TODO (7) Create a public static final String call COLUMN_HUMIDITY with the value "humidity"

//      TODO (8) Create a public static final String call COLUMN_PRESSURE with the value "pressure"

//      TODO (9) Create a public static final String call COLUMN_WIND_SPEED with the value "wind"

//      TODO (10) Create a public static final String call COLUMN_DEGREES with the value "degrees"

        }
    }
