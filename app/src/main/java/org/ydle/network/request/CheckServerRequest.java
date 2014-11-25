package org.ydle.network.request;

import com.android.volley.toolbox.JsonObjectRequest;

import org.ydle.network.listener.DefaultListener;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class CheckServerRequest extends JsonObjectRequest {

    private static final String LOG_TAG = CheckServerRequest.class.getSimpleName();

    private String url;

    public CheckServerRequest(String url, DefaultListener listener) {
        super(Method.GET, null, null, listener, listener);
        this.url = url;
    }

    public String getUrl() {
        return "http://" + url + "/api/rooms";
    }
}
