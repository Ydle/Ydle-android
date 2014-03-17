package org.ydle.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import org.ydle.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class StringUtils {

    private static final String LOG_TAG = StringUtils.class.getSimpleName();

    public static String streamToString(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            if (Config.displayLog()) {
                Log.d(LOG_TAG, "streamToString", e);
            }
        }

        return sb.toString();
    }
}
