package com.example.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
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


    /* * Esta constante se usará para hacer coincidir los URI con los datos que están buscando. Nosotros lo tomaremos
     * ventaja de la clase UriMatcher para que al hacer coincidir sea más fácil que hacer algo
     * nosotros mismos, como el uso de expresiones regulares.
     */

    private WeatherDbHelper mOpenHelper;
    private static final int CODE_WEATHER = 001;
    private static final int CODE_WEATHER_WITH_DATE = 002;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /*
            * Todas las rutas agregadas al UriMatcher tienen un código correspondiente para regresar cuando se
             * produce una coincidencia
         * encontró. El código pasado al constructor de UriMatcher aquí representa el código para
         * retorno para el URI raíz. Es común usar NO_MATCH como código para este caso.
         */
    
    public static UriMatcher buildUriMatcher() {
        final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authentication = WeatherContract.CONTENT_AUTHORITY;

        /*
         * Para cada tipo de URI que desee agregar, cree un código correspondiente. Preferiblemente, estos son
                * campos constantes en su clase para que pueda usarlos durante toda la clase y no
         * ellos no van a cambiar En Sunshine, usamos CODE_WEATHER o CODE_WEATHER_WITH_DATE.
                * /
        / * Este URI es contenido: //com.example.android.sunshine/weather */
        mUriMatcher.addURI(authentication, WeatherContract.PATH_WEATHER, CODE_WEATHER);

        /*
         * Este URI se vería como contenido: //com.example.android.sunshine/weather/1472214172
         * El "/ #" significa para el UriMatcher que si PATH_WEATHER es seguido por CUALQUIER número,
         * que debería devolver el código CODE_WEATHER_WITH_DATE
         */

        mUriMatcher.addURI(authentication, WeatherContract.PATH_WEATHER + "/#", CODE_WEATHER_WITH_DATE);


        return mUriMatcher;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numberRow = 0;
        int uriType = sUriMatcher.match(uri);

        switch (uriType) {

//          COMPLETED (2) Only perform our implementation of bulkInsert if the URI matches the CODE_WEATHER code
            case CODE_WEATHER:
                db.beginTransaction();
                    try {
                        for (ContentValues value : values) {
                            long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, value);
                            if(id != -1) {
                                numberRow++;
                            }
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                    if (numberRow > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                    return numberRow;
                    default:
                        return super.bulkInsert(uri, values);
                }


           /* (1) Implement the bulkInsert method

//              (3) Return the number of rows inserted from our implementation of bulkInsert

//          (4) If the URI does match match CODE_WEATHER, return the super implementation of bulkInsert */

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
        // TODO (1) Implement the delete method of the ContentProvider


//          TODO (2) Only implement the functionality, given the proper URI, to delete ALL rows in the weather table

//      TODO (3) Return the number of rows deleted

        int rowNumberDelete;

        int uriType = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        if (null == s) s = "1";

        switch (uriType) {
            case CODE_WEATHER:

                rowNumberDelete = db.delete(WeatherContract.WeatherEntry.TABLE_NAME, s, strings);
                break;
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (rowNumberDelete != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowNumberDelete;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}

