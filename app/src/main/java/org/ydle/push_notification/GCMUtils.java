package org.ydle.push_notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.ydle.config.Config;

import java.io.IOException;

/**
 * Created by Jean-Baptiste on 31/07/2014.
 */
public class GCMUtils {

    private static final String TAG = "GCMUtils";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PREFS_NAME = "GCM_YDLE";
    public static final String PREFS_PROPERTY_REG_ID = "registration_id";



    /**
     * Check the device to make sure it has the Google Play Services APK. If it
     * doesn't, display a dialog that allows users to download the APK from the
     * Google Play Store or enable it in the device's system settings.
     */
    private static boolean checkPlayServices(Activity activity)
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            }
            else
            {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service, if there
     * is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private static String getRegistrationId(Context context)
    {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PREFS_PROPERTY_REG_ID, "");
        if (registrationId == null || registrationId.equals(""))
        {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        return registrationId;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context
     *            application's context.
     * @param regId
     *            registration ID
     */
    private static void storeRegistrationId(Context context, String regId)
    {
        final SharedPreferences prefs = getGcmPreferences(context);
        Log.i(TAG, "Saving regId : " + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_PROPERTY_REG_ID, regId);
        editor.commit();
    }

    /**
     * Removes the registration ID from the application's
     * {@code SharedPreferences}.
     * @param context
     * 		the application context
     */
    private static void removeRegistrationId(Context context)
    {
        final SharedPreferences prefs = getGcmPreferences(context);
        Log.i(TAG, "Removig regId");
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PREFS_PROPERTY_REG_ID);
        editor.commit();
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private static void registerInBackground(Context context)
    {
        new AsyncTask<Context, Void, String>()
        {
            @Override
            protected String doInBackground(Context... params)
            {
                Context context = params[0];
                String msg = "";
                try
                {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

                    String regid = gcm.register(Config.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over
                    // HTTP, so it can use GCM/HTTP or CCS to send messages to your app.
                    //sendRegistrationIdToBackend();

                    // For this demo: we use upstream GCM messages to send the
                    // registration ID to the 3rd party server

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        }.execute(context, null, null);
    }


    /**
     * @return Application's {@code SharedPreferences}.
     */
    private static SharedPreferences getGcmPreferences(Context context)
    {
        // This sample app persists the registration ID in shared preferences,
        // but how you store the regID in your app is up to you.
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Send an upstream GCM message to the 3rd party server to remove this
     * device's registration ID, and contact the GCM server to do the same.
     */
    private static void unregister(String regid)
    {
        Log.d(TAG, "UNREGISTER USERID: " + regid);
        new AsyncTask<Context, Void, String>()
        {
            Context context;
            @Override
            protected String doInBackground(Context... params)
            {
                context = params[0];
                String msg = "";
                try
                {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                    gcm.unregister();
                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg)
            {
                removeRegistrationId(context);
            }
        }.execute();
    }


    public static void register(Activity activity) {
        if (checkPlayServices(activity)) {
            if(getRegistrationId(activity).equals("")) {
                registerInBackground(activity);
            }
        }
    }
}
