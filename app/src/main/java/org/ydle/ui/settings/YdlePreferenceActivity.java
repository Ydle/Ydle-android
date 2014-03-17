package org.ydle.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import org.ydle.application.YdleApplication;
import org.ydle.network.manager.NetworkManager;

import java.util.Observable;
import java.util.Observer;

public class YdlePreferenceActivity extends PreferenceActivity implements Observer {

    private static final String LOG_TAG = YdlePreferenceActivity.class.getSimpleName();

    protected NetworkManager mNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkManager = YdleApplication.getNetworkManager();
        mNetworkManager.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {}
}
