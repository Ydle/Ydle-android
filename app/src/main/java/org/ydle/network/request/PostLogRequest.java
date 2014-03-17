package org.ydle.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.ydle.data.model.Log;
import org.ydle.data.model.Room;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.listener.DefaultListener;

import java.util.Map;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class PostLogRequest extends JsonObjectRequest{

    private static final String LOG_TAG = PostLogRequest.class.getSimpleName();

    private Map<String, String> mParams;

    public PostLogRequest(DefaultListener listener, Log log) {
        super(Method.POST, null, null, listener, listener);
        mParams.put("level", String.valueOf(log.getLevel().getValue()));
        mParams.put("message", log.getMessage());
    }

    public String getUrl() {
        return "http://" + YdlePreferences.getServerAddress() + "/api/logs/add";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
