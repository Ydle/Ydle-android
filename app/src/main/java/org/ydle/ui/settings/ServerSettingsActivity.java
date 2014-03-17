package org.ydle.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.manager.NetworkManager;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class ServerSettingsActivity extends YdlePreferenceActivity {

    private static final String LOG_TAG = ServerSettingsActivity.class.getSimpleName();

    public static int CODE_RETOUR = 1;

    public static void displayServerSettingsActivity(final Activity from) {
        final Intent i = new Intent(from, ServerSettingsActivity.class);
        from.startActivityForResult(i, CODE_RETOUR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_server);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DisplayUtils.displayToast(ServerSettingsActivity.this, "TODO: Check server");
//        DisplayUtils.showProgressDialog(ServerSettingsActivity.this, getString(R.string.loading));
//        mNetworkManager.callGetAllRooms();
    }

    private void manageResponse(boolean isValid) {
        DisplayUtils.dismissProgressDialog();
        if (isValid) {
            DisplayUtils.displayToast(ServerSettingsActivity.this, getString(R.string.first_use_valid_server));
            super.onBackPressed();
        } else {
            DisplayUtils.displayToast(ServerSettingsActivity.this, getString(R.string.error_invalid_server));
        }
    }


    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.GetAllRoomsSuccess) {
                manageResponse(true);
            } else if (data == NetworkManager.NetworkResult.GetAllRoomsError) {
                manageResponse(false);
            }
        }
    }
}
