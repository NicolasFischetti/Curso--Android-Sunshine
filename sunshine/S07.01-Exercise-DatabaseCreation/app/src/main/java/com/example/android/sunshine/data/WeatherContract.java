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

    La "autoridad de contenido" es un nombre para todo el proveedor de contenido, similar al
     * relación entre un nombre de dominio y su sitio web. Una cadena conveniente para usar para
     * la autoridad de contenido es el nombre del paquete para la aplicación, que se garantiza que es único en el
     * Tienda de juegos
Usa CONTENT_AUTHORITY para crear la base de todos los URI que las aplicaciones usarán para contactar
     * el proveedor de contenido para Sunshine

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

        /*
        * /* La base CONTENT_URI utilizada para consultar la tabla Meteorología del proveedor de contenido */
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

        /*
                * Crea un URI que agrega la fecha del tiempo hasta el final de la ruta del URI del contenido del pronóstico.
         * Esto se usa para consultar detalles sobre una entrada de clima individual por fecha. Esto es lo que
         * uso para la consulta de vista detallada. Suponemos que se pasa una fecha normalizada a este método.
                *
                * @param date Fecha normalizada en milisegundos
         * @return Uri para consultar detalles sobre una sola entrada en el clima
         */

        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }

        /*
                * Devuelve solo la parte de selección de la consulta meteorológica de un valor normalizado de hoy.
                * Esto se usa para obtener un pronóstico del tiempo a partir de la fecha de hoy. Para hacer esto fácil de usar
         * en la selección de compuestos, incorporamos la fecha de hoy como un argumento en la consulta.
         *
                 * @return La parte de selección de la consulta meteorológica para hoy en adelante
         */

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
