package com.example.android.sunshine;

import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private TextView mDateView;
    private TextView mDescriptionView;
    private TextView mHighTemperatureView;
    private TextView mLowTemperatureView;
    private TextView mHumidityView;
    private TextView mWindView;
    private TextView mPressureView;
    private Uri mUri;

    private String mForecast;

    private static final int ID_DETAIL_LOADER = 100;

    private static final String[] MAIN_DETAIL_PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEEDLE,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
            WeatherContract.WeatherEntry.COLUMN_WEATHER
    };

    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_HUMIDITY = 3;
    public static final int INDEX_WEATHER_PRESSURE = 4;
    public static final int INDEX_WEATHER_WIND_SPEEDLE = 5;
    public static final int INDEX_WEATHER_DEGREES = 6;
    public static final int INDEX_WEATHER_CONDITION_ID = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDateView = (TextView) findViewById(R.id.date);
        mDescriptionView = (TextView) findViewById(R.id.weather_description);
        mHighTemperatureView = (TextView) findViewById(R.id.high_temperature);
        mLowTemperatureView = (TextView) findViewById(R.id.low_temperature);
        mHumidityView = (TextView) findViewById(R.id.humidity);
        mWindView = (TextView) findViewById(R.id.wind);
        mPressureView = (TextView) findViewById(R.id.pressure);

        mUri = getIntent().getData();

        if(mUri == null) {
            throw new NullPointerException("URI for DetailActivity cannot be null");
        }
    }

    /**
     * Uses the ShareCompat Intent builder to create our Forecast intent for sharing. We set the
     * type of content that we are sharing (just regular text), the text itself, and we return the
     * newly created Intent.
     *
     * @return The Intent to use to start our share.
     */
    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORECAST_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        int loaderDetail = ID_DETAIL_LOADER;

        switch (loaderDetail) {
            case ID_DETAIL_LOADER:

            Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;
            /* Sort order: Ascending by date */
            String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";

            String section = WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();

            return new CursorLoader(this, forecastQueryUri,MAIN_DETAIL_PROJECTION,
                    section, null, sortOrder);
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderDetail);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        boolean dataCursor = false;
        if(data != null && data.moveToFirst()) {
            dataCursor = true;
        }
        if(!(dataCursor)){
            return;
        }

        long date =  data.getLong(DetailActivity.INDEX_WEATHER_DATE);
        String dateString = SunshineDateUtils.getFriendlyDateString(this, date, false);
        mDescriptionView.setText(dateString);

        double high = data.getDouble(DetailActivity.INDEX_WEATHER_MAX_TEMP);
        double low = data.getDouble(DetailActivity.INDEX_WEATHER_MIN_TEMP);

        String highTemperature = SunshineWeatherUtils.formatTemperature(this, high);
        mHighTemperatureView.setText(highTemperature);

        String lowTemperature = SunshineWeatherUtils.formatTemperature(this, low);
        mLowTemperatureView.setText(lowTemperature);

        int id = data.getInt(DetailActivity.INDEX_WEATHER_CONDITION_ID);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(this, id);
        mDescriptionView.setText(description);

        float humidity = data.getFloat(DetailActivity.INDEX_WEATHER_HUMIDITY);
        String humidityString =  getString(R.string.format_humidity, humidity);
        mHumidityView.setText(humidityString);

        float windSpeed = data.getFloat(INDEX_WEATHER_WIND_SPEEDLE);
        float windDirection = data.getFloat(INDEX_WEATHER_DEGREES);
        String windString = SunshineWeatherUtils.getFormattedWind(this, windSpeed, windDirection);
        mWindView.setText(windString);

        float pressure = data.getFloat(INDEX_WEATHER_PRESSURE);
        String pressureString = getString(R.string.format_pressure, pressure);
        mPressureView.setText(pressureString);

        mForecast = String.format("%s - %s - %s/%s",
                dateString, description, highTemperature, lowTemperature);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}