package org.ydle.network.listener;

        import android.util.Log;

        import com.android.volley.Response;
        import com.android.volley.VolleyError;

        import org.json.JSONObject;
        import org.ydle.config.Config;

/**
 * Created by Jean-Baptiste on 31/01/14.
 */
public class DefaultListener implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String LOG_TAG = DefaultListener.class.getSimpleName();

    @Override
    public void onResponse(JSONObject response) {
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "onResponse");
            Log.d(LOG_TAG, response.toString());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "onResponse");
            Log.d(LOG_TAG, "" + error.getMessage());
        }
    }
}
