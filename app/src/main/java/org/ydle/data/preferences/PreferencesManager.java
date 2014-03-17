package org.ydle.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class PreferencesManager {

    private static final String LOG_TAG = PreferencesManager.class.getSimpleName();

    private static SharedPreferences mSharedPreferences;
    private static Context mContext;

    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";

    protected static void init(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected static void setPreference(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(value));
        editor.commit();
    }

    protected static void setPreference(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(Integer.toString(value)));
        editor.commit();
    }

    protected static void setPreference(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(Long.toString(value)));
        editor.commit();
    }

    protected static void setPreference(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(Boolean.toString(value)));
        editor.commit();
    }

    protected static String getStringPreference(String key, String default_value) {
        String value = mSharedPreferences.getString(encrypt(key), null);
        return value != null ? decrypt(value) : default_value;
    }

    protected static int getIntPreference(String key, int default_value) {
        String value = mSharedPreferences.getString(encrypt(key), null);
        return value != null ? Integer.parseInt(decrypt(value)) : default_value;
    }

    protected static long getLongPreference(String key, long default_value) {
        String value = mSharedPreferences.getString(encrypt(key), null);
        return value != null ? Long.parseLong(decrypt(value)) : default_value;
    }

    protected static boolean getBooleanPreference(String key, boolean default_value) {
        String value = mSharedPreferences.getString(encrypt(key), null);
        return value != null ? Boolean.parseBoolean(decrypt(value)) : default_value;
    }

    private static String encrypt(String input) {
        //TODO encrypt
        return input;
    }

    private static String decrypt(String input) {
        //TODO decrypt
        return input;
    }
}
