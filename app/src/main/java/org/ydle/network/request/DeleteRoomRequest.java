package org.ydle.network.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.ydle.data.model.Room;
import org.ydle.data.preferences.YdlePreferences;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class DeleteRoomRequest extends StringRequest{

    private static final String LOG_TAG = DeleteRoomRequest.class.getSimpleName();

    private Room mRoom;

    public DeleteRoomRequest(Response.Listener listener, Response.ErrorListener errorListener, Room room) {
        super(Method.DELETE, null, listener, errorListener);
        mRoom = room;
    }

    public String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/room/" + mRoom.getId();
    }
}
