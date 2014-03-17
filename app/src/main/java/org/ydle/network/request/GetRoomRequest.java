package org.ydle.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
import org.ydle.data.model.Room;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class GetRoomRequest extends JsonObjectRequest{

    private static final String LOG_TAG = GetRoomRequest.class.getSimpleName();

    private int mId;

    public GetRoomRequest(DefaultListener listener, int id) {
        super(Method.GET, null, null, listener, listener);
        mId = id;
    }

    public  String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/room/" + mId;
    }
}
