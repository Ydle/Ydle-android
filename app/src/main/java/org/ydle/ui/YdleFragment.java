package org.ydle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.ydle.R;
import org.ydle.application.YdleApplication;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.settings.HomeSettingsActivity;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;
import java.util.Observer;

public class YdleFragment extends Fragment implements Observer{

    private static final String LOG_TAG = YdleFragment.class.getSimpleName();

    protected NetworkManager mNetworkManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkManager = YdleApplication.getNetworkManager();
        mNetworkManager.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {}
}
