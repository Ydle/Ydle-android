package org.ydle.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.ydle.data.model.Room;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

import java.util.Map;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class PutRoomRequest extends JsonObjectRequest{

    private static final String LOG_TAG = PutRoomRequest.class.getSimpleName();

    private Room mRoom;
    private Map<String, String> mParams;

    public PutRoomRequest(DefaultListener listener, Room room) {
        super(Method.PUT, null, null, listener, listener);
        mParams.put("name", room.getName());
        mParams.put("description", room.getDescription());
        mParams.put("type_id", String.valueOf(room.getType().getId()));
        mParams.put("is_active", String.valueOf(room.isActive()));
        mRoom = room;
    }

    public  String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/room/" + mRoom.getId();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
