package org.ydle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.ydle.R;
import org.ydle.application.YdleApplication;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.settings.HomeSettingsActivity;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;
import java.util.Observer;

public class YdleFragmentActivity extends FragmentActivity implements Observer{

    private static final String LOG_TAG = YdleFragmentActivity.class.getSimpleName();

    protected NetworkManager mNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkManager = YdleApplication.getNetworkManager();
        mNetworkManager.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            HomeSettingsActivity.displayHomeSettingsActivity(YdleFragmentActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == HomeSettingsActivity.CODE_RETOUR) {
            DisplayUtils.displayToast(this.getApplicationContext(), getString(R.string.preferences_saved));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void update(Observable observable, Object data) {}
}
