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
package com.example.android.sunshine;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {


    private final Context mContext;

    private Cursor mCursor;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private ForecastAdapterOnClickHandler mClickHandler;


    /**
     * The interface that receives onClick messages.
     */
    public interface ForecastAdapterOnClickHandler { // un click para cada dia
        void onClick(long date);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public ForecastAdapter(Context context,ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler; // crea el adaptador para hacer un click en la data
        mContext = context;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final TextView weatherSummary;

        ForecastAdapterViewHolder(View view) {
            super(view);

            weatherSummary = (TextView) view.findViewById(R.id.tv_weather_data);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) { // v es la View que fue clikeada. Esto se llama por las vistas secundarios haciendo un click.
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            long dateMillis = mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
            mClickHandler.onClick(dateMillis);
        }
    }


    // Esto es llamado cuando una nueva ViewHolder es creada. Se crean los suficientes ViewHolder para llenar la screen.
    // El viewGroup a estos ViewHolders
    // El viewType nos permite integrar diferentes tipos de item.
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view);
    }


    // Sirve para desplayar la data en una posicion especifica. Se actualiza el content de las ViewHolder para mostrar
    // los detalles del tiempo en su posicion particular. Tomando la posicion la cual es pasada por parametro.
    // se llama al ForecastAdapterViewHolder para representar el contenido del item en la posicion dada.
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
            mCursor.moveToPosition(position);

          long date =  mCursor.getLong(MainActivity.INDEX_WEATHER_DATE);
        String dateString = SunshineDateUtils.getFriendlyDateString(mContext, date, false);
          double high = mCursor.getDouble(MainActivity.INDEX_WEATHER_MAX_TEMP);
        double low = mCursor.getDouble(MainActivity.INDEX_WEATHER_MIN_TEMP);
        String HighLow = SunshineDateUtils.formatHighLows(mContext, high, low);

        int id = mCursor.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(mContext, id);

        String weatherSummary = dateString + " - " + description + " - " + HighLow;

        forecastAdapterViewHolder.weatherSummary.setText(weatherSummary);
    }

    // Retorna el numero de items que se muestra
    @Override
    public int getItemCount() {
            if (null == mCursor) return 0;
            return mCursor.getCount();

       /* if (null == mWeatherData) return 0;
        return mWeatherData.length; */
    }

    void SwapCursors (Cursor Newcursor) {
        mCursor = Newcursor;
        notifyDataSetChanged();
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param weatherData The new weather data to be displayed.
     */

    //Este metodo setea el forecast del clima en un forecastAdapter si ya esta creado. Sirve para
    // cuando recibimos nueva data de la web y no qqueremos crear un nuevo metodo.
   /* public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }*/

}



//  TODO (1) Declare a private final Context field called mContext
//  TODO (2) Declare a private Cursor field called mCursor
//  TODO (3) Add a Context field to the constructor and store that context in mContext
//  TODO (5) Delete the current body of onBindViewHolder
//      TODO (6) Move the cursor to the appropriate position
//      TODO (7) Generate a weather summary with the date, description, high and low
// TODO (8) Display the summary that you created above
//      TODO (9) Delete the current body of getItemCount
//      TODO (10) If mCursor is null, return 0. Otherwise, return the count of mCursor
//  TODO (11) Create a new method that allows you to swap Cursors.
//      TODO (12) After the new Cursor is set, call notifyDataSetChanged
//  TODO (13) Instead of passing the String from the data array, use the weatherSummary text!
//  TODO (14) Remove the mWeatherData declaration and the setWeatherData method