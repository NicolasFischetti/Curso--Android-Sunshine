

//  TODO (10) Add constant values to sync Sunshine every 3 - 4 hours

//  TODO (11) Add a sync tag to identify our sync job

//  TODO (12) Create a method to schedule our periodic weather sync

//  TODO (13) Call the method you created to schedule a periodic weather sync

package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.example.android.sunshine.data.WeatherContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;


public class SunshineSyncUtils {

    private static boolean sInitialized;

    private static final int VALUES_HOURS_THREE = 3;
    private static final int VALUES_SECONDS_THREE = (int) TimeUnit.HOURS.toSeconds(VALUES_HOURS_THREE);
    private static final int VALUES_FLEXTIME_SECONDS_THREE = VALUES_SECONDS_THREE / 3;

    private static final String SUNSHINE_SYNC_TAG = "sunshineSync";


    static void firebaseDispatcher (final Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job newJob = dispatcher.newJobBuilder()
                .setService(SunshineFirebaseJobService.class)
                .setTag(SUNSHINE_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(VALUES_SECONDS_THREE, VALUES_HOURS_THREE + VALUES_FLEXTIME_SECONDS_THREE))
                .setReplaceCurrent(true)
                .build();

        dispatcher.mustSchedule(newJob);
    }


    synchronized public static void initialize (final Context context) {

        if(sInitialized)
            return;
        sInitialized= true;

        firebaseDispatcher(context);

        Uri weatherProvider = WeatherContract.WeatherEntry.CONTENT_URI;

        String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
        String selectionStatement = WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();

        /* Here, we perform the query to check to see if we have any weather data */
        Cursor cursor = context.getContentResolver().query(
                weatherProvider,
                projectionColumns,
                selectionStatement,
                null,
                null);

        if(weatherProvider.equals(Uri.EMPTY) || cursor == null || cursor.getCount() == 0) {
            startImmediateSync(context);
        }

    }

//  TODO (1) Declare a private static boolean field called sInitialized
//  TODO (2) Create a synchronized public static void method called initialize
    //  TODO (3) Only execute this method body if sInitialized is false
    //  TODO (4) If the method body is executed, set sInitialized to true
    //  TODO (5) Check to see if our weather ContentProvider is empty
    //  TODO (6) If it is empty or we have a null Cursor, sync the weather now!

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param context The Context used to start the IntentService for the sync.
     */
    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, SunshineSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}