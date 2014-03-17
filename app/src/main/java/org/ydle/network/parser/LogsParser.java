package org.ydle.network.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.data.model.Log;
import org.ydle.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class LogsParser {

    private static final String LOG_TAG = LogsParser.class.getSimpleName();

    //TODO Logs

    public static ArrayList<Log> parseAllLogs(JSONObject root) {
        if (root.optJSONArray("logs") != null) {
            try {
                return parseAllLogs(root.getJSONArray("logs"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static ArrayList<Log> parseAllLogs(JSONArray array) {
        ArrayList<Log> logsList = null;

        if (array.length() > 0) {
            logsList = new ArrayList<Log>();
            Log log;
            for (int i = 0; i < array.length(); i++) {
                try {
                    log = parseALog(array.getJSONObject(i));
                    if (log != null) {
                        logsList.add(log);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return logsList;
    }

    public static Log parseALog(JSONObject item) throws JSONException {
        if (item != null) {
            int level = JSONUtils.getOptionalInt(item, "level");
            String message = JSONUtils.getOptionalString(item, "name");
            return new Log(Log.LogLevel.getFromValue(level), message);

        }
        return null;
    }

}
