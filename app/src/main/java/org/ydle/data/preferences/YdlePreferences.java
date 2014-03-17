package org.ydle.data.preferences;

import android.content.Context;

import org.ydle.R;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class YdlePreferences {

    private static final String LOG_TAG = PreferencesManager.class.getSimpleName();

    private static PreferencesManager mPreferencesManager;
    private static Context mContext;

    private static String PREFERENCE_SERVER;
    private static String PREFERENCE_PLUGINS;
    private static String PREFERENCE_CACHE;
    private static String PREFERENCE_CHARTS;
    private static String PREFERENCE_NEWS;
    private static String PREFERENCE_ABOUT;
    private static String PREFERENCE_SERVER_NAME;
    private static String PREFERENCE_SERVER_ADDRESS;
    private static String PREFERENCE_SERVER_PORT;
    private static String PREFERENCE_SERVER_LOGIN;
    private static String PREFERENCE_SERVER_PATH;
    private static String PREFERENCE_YANA;
    private static String PREFERENCE_SARAH;
    private static final String PREFERENCE_FIRST_USE = "PREFERENCE_FIRST_USE";

    public static void init(Context context) {
        mContext = context;
        mPreferencesManager.init(context);
        PREFERENCE_SERVER = mContext.getString(R.string.PREFERENCE_SERVER);
        PREFERENCE_PLUGINS = mContext.getString(R.string.PREFERENCE_PLUGINS);
        PREFERENCE_CACHE = mContext.getString(R.string.PREFERENCE_CACHE);
        PREFERENCE_CHARTS = mContext.getString(R.string.PREFERENCE_CHARTS);
        PREFERENCE_NEWS = mContext.getString(R.string.PREFERENCE_NEWS);
        PREFERENCE_ABOUT = mContext.getString(R.string.PREFERENCE_ABOUT);
        PREFERENCE_SERVER_NAME = mContext.getString(R.string.PREFERENCE_SERVER_NAME);
        PREFERENCE_SERVER_ADDRESS = mContext.getString(R.string.PREFERENCE_SERVER_ADDRESS);
        PREFERENCE_SERVER_PORT = mContext.getString(R.string.PREFERENCE_SERVER_PORT);
        PREFERENCE_SERVER_LOGIN = mContext.getString(R.string.PREFERENCE_SERVER_LOGIN);
        PREFERENCE_SERVER_PATH = mContext.getString(R.string.PREFERENCE_SERVER_PATH);
        PREFERENCE_YANA = mContext.getString(R.string.PREFERENCE_YANA);
        PREFERENCE_SARAH = mContext.getString(R.string.PREFERENCE_SARAH);
    }

    public static String getServerName() {
        return PreferencesManager.getStringPreference(PREFERENCE_SERVER_NAME, "Ydle");
    }

    public static void setServerName(String value) {
        PreferencesManager.setPreference(PREFERENCE_SERVER_NAME, value);
    }

    public static String getServerAddress() {
        return PreferencesManager.getStringPreference(PREFERENCE_SERVER_ADDRESS, "demo.ydle.fr");
    }

    public static void setServerAddress(String value) {
        PreferencesManager.setPreference(PREFERENCE_SERVER_ADDRESS, value);
    }

    public static String getServerPort() {
        return PreferencesManager.getStringPreference(PREFERENCE_SERVER_PORT, "");
    }

    public static String getServerLogin() {
        return PreferencesManager.getStringPreference(PREFERENCE_SERVER_LOGIN, "");
    }

    public static boolean isFirstUse() {
        return PreferencesManager.getBooleanPreference(PREFERENCE_FIRST_USE, true);
    }

    public static void setFirstUse(boolean value) {
        PreferencesManager.setPreference(PREFERENCE_FIRST_USE, value);
    }
}
