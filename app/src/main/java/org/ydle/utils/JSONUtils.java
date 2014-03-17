package org.ydle.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class JSONUtils {

    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    //TODO Logs

    public static boolean getOptionalBoolean(JSONObject item, String name) {
        try {
            return item.getBoolean(name);
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getOptionalString(JSONObject item, String name) {
        try {
            String value = item.getString(name);
            if (value.equals("null")) {
                return "";
            }
            return value;
        } catch (JSONException e) {
            return "";
        }
    }

    public static int getOptionalInt(JSONObject item, String name) {
        try {
            return item.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }

    public static long getOptionalLong(JSONObject item, String name) {
        try {
            return item.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }
}
