package org.ydle.application;

import android.app.Application;

import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.manager.NetworkManager;

/**
 * Created by Jean-Baptiste on 28/01/14.
 */
public class YdleApplication extends Application{

    private static final String LOG_TAG = YdleApplication.class.getSimpleName();

    private static NetworkManager mNetworkManager;

    @Override
    public void onCreate() {
        super.onCreate();

        YdlePreferences.init(getApplicationContext());
        mNetworkManager = new NetworkManager(getApplicationContext());
    }

    public static NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

}
