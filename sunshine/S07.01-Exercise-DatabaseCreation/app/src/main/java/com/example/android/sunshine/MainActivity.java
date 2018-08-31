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
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        ForecastAdapter.ForecastAdapterOnClickHandler,
        LoaderCallbacks<String[]>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static final int FORECAST_LOADER_ID = 0;

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // El linearlayoutmanager es responsable juntar y posicionar los items dentro del Recycler en una lista linear
        // Puede producir una lista tanto vertical como horizontal dependiendo que parametro que le pases.

        int recyclerViewOrientation = LinearLayoutManager.VERTICAL;

        boolean shouldReverseLayout = false; // true si queres hacer tu layout reversible. Generalmente se hace cuando hay una lista horizontal
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);
        mRecyclerView.setLayoutManager(layoutManager);


         // si sabes que va a  cambiar el content no tenes que cambiar el tamaño de los hijos del layout
        mRecyclerView.setHasFixedSize(true);

        // El forecastAdapter nos sirve para linkear la data con la view que va a terminar mostarando nuestra data
        mForecastAdapter = new ForecastAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mForecastAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /*
         * This ID will uniquely identify the Loader. */
        int loaderId = FORECAST_LOADER_ID;


        /* Desde MainActivity, hemos implementado la interfaz LoaderCallbacks con el tipo de
         * Matriz de cadenas (implementa LoaderCallbacks <String []>) Se pasa la devolución de llamada variable
                * a la llamada a initLoader a continuación. Esto significa que siempre que el loaderManager tenga
         * algo para notificarnos, lo hará a través de esta devolución de llamada*/


        LoaderCallbacks<String[]> callback = MainActivity.this;


        /* se pude usar el Bundle para el initLoader el cual podes tener acceso desde el onCreateLoader callback. */
        Bundle bundleForLoader = null;


        /*
         * Asegura que un loader esté inicializado y activo. Si el loader no existe ya, uno es
                * creado y (si la actividad / fragmento está actualmente iniciado) inicia el cargador. De otra manera
                * el último cargador creado se reutiliza.
                */
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

        Log.d(TAG, "onCreate: registering preference changed listener");

        /*Registre MainActivity como OnPreferenceChangedListener para recibir una devolución de llamada cuando
                * SharedPreference ha cambiado. Tenga en cuenta que debemos anular el registro de MainActivity como
                * OnSharedPreferenceChanged  en onDestroy para evitar fugas de memoria.*/

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }
    /*
        * Crear una instancia y devolver un nuevo loader para la identificación dada.
            *
            * @param id El ID cuyo loader se va a crear.
     * @param loaderArgs Cualquier argumento proporcionado por la persona que llama.
     *
             * @return Devuelve una nueva instancia de Loader que está lista para comenzar a cargarse.
     */
    @Override
    public Loader<String[]> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<String[]>(this) {


            /*Esta matriz de cadenas mantendrá y ayudará a almacenar en caché nuestros datos meteorológicos */
            String[] mWeatherData = null;

            /*La subclass de AsyncTaskLoader debe iomplementar esto para hacerse cargo de cargar su data*/
            @Override
            protected void onStartLoading() {
                if (mWeatherData != null) {
                    deliverResult(mWeatherData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            /**
             *
             Este es el método del AsyncTaskLoader que cargará y analizará los datos JSON
             * desde OpenWeatherMap en segundo plano(backgorund)
             *
             * @return Devuelve Weather data del OpenWeatherMap como un array de string.
             *         Si ocurre un error devuelve nulo
             */
            @Override
            public String[] loadInBackground() {

                URL weatherRequestUrl = NetworkUtils.getUrl(MainActivity.this);

                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(weatherRequestUrl);

                    String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                            .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                    return simpleJsonWeatherData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /**
             * Sends the result of the load to the registered listener.
             * Envia el resultado de la carga al registered listener
             * @param data Es ek resultado de la data que cargo
             */
            public void deliverResult(String[] data) {
                mWeatherData = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * Called when a previously created loader has finished its load.
     * Se lo llama cuando con anterioridad se creo el loader y este termino su carga.
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mForecastAdapter.setWeatherData(data);
        if (null == data) {
            showErrorMessage();
        } else {
            showWeatherDataView();
        }
    }

    /**
     *
     Se llama cuando un loader creado previamente se restablece, y por lo tanto
     * haciendo que sus datos no estén disponibles. La aplicación debería en este momento
     * eliminar cualquier referencia que tenga a los datos del loader.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

    /**
     * Este método se utiliza cuando estamos restableciendo los datos, de modo que en un punto en el tiempo durante un
     * actualización de nuestros datos, puede ver que no hay datos que se muestran.
     */
    private void invalidateData() {
        mForecastAdapter.setWeatherData(null);
    }

    /**
     * This method uses the URI scheme for showing a location found on a map in conjunction with
     * an implicit Intent.
     * */
    private void openLocationInMap() {
        String addressString = SunshinePreferences.getPreferredWeatherLocation(this);
        Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
        }
    }

    /**
     * Este metodo se usa para responder a los clicks de la lista.
     * @param weatherForDay describe los datos particulares de un dia/lugar
     */
    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, weatherForDay);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * OnStart is called when the Activity is coming into view. This happens when the Activity is
     * first created, but also happens when the Activity is returned to from another Activity. We
     * are going to use the fact that onStart is called when the user returns to this Activity to
     * check if the location setting or the preferred units setting has changed. If it has changed,
     * we are going to perform a new query.
     */
    @Override
    protected void onStart() {
        super.onStart();

        /*
         * Cuando las preferencias fueron cambiadas, chequea que esten actualizadas y luego, hace un loader y pasa
         * las prefencias a false porque ya han sido actualizadas.
         */
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*  Anule el registro de MainActivity como OnPreferenceChangedListener para evitar fugas de memoria */
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Usar AppCompatActivity para encargarse de inflar el menu a traves del getMenuInflater
        MenuInflater inflater = getMenuInflater();
        /*Menu Inflanter : Esta clase se utiliza para crear instancias de archivos XML de menú en objetos de menú(items).
        /* Utilice el método de inflar del inflador para inflar el diseño de forecast(setting, map location) a este menú */
        inflater.inflate(R.menu.forecast, menu);
        /* Retornar true si el menu aparece en la ToolBar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            invalidateData();
            getSupportLoaderManager().restartLoader(FORECAST_LOADER_ID, null, this);
            return true;
        }

        if (id == R.id.action_map) {
            openLocationInMap();
            return true;
        }

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        /*
         *  Al setear esto a true esto puede controlar los retornos al MainActivity, y por lo tanto puede actualizar la data
         *
         */
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }
}