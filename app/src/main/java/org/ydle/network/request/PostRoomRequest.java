package org.ydle.network.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.ydle.data.model.Room;
import org.ydle.data.model.Sensor;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class PostRoomRequest extends StringRequest{

    private static final String LOG_TAG = PostDataRequest.class.getSimpleName();

    private Map<String, String> mParams;
    private Room mRoom;

    public PostRoomRequest(Response.Listener listener, Response.ErrorListener errorListener, Room room) {
        super(Method.POST, null, listener, errorListener);
        mRoom = room;
    }

    public String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/room";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        mParams = new HashMap<String, String>();
        mParams.put("name", String.valueOf(mRoom.getName()));
        mParams.put("description", String.valueOf(mRoom.getDescription()));
        mParams.put("type_id", String.valueOf(mRoom.getType().getId()));
        mParams.put("is_active", String.valueOf(mRoom.isActive()));
        Log.d("params", mParams.toString());
        return mParams;
    }
}
