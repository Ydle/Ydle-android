package org.ydle.network.request;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class GetAllRoomsRequest extends JsonObjectRequest {

    private static final String LOG_TAG = GetAllRoomsRequest.class.getSimpleName();

    public GetAllRoomsRequest(DefaultListener listener) {
        super(Request.Method.GET, null, null, listener, listener);
    }

    public  String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/rooms";
    }
}
