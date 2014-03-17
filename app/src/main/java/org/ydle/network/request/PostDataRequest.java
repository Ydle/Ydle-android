package org.ydle.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.ydle.data.model.Room;
import org.ydle.data.model.Sensor;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

import java.util.Map;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class PostDataRequest extends JsonObjectRequest{

    private static final String LOG_TAG = PostDataRequest.class.getSimpleName();

    private Map<String, String> mParams;

    public PostDataRequest(DefaultListener listener, int sender, Sensor sensor) {
        super(Method.POST, null, null, listener, listener);
        mParams.put("sender", String.valueOf(sender));
        mParams.put("type", String.valueOf(sensor.getId()));
        mParams.put("data", String.valueOf(sensor.getCurrentValue()));
    }

    public String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/node/data";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
