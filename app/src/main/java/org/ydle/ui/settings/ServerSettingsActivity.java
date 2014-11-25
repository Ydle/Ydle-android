package org.ydle.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.Log;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.network.manager.NetworkManager;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class ServerSettingsActivity extends YdlePreferenceActivity implements Preference.OnPreferenceChangeListener{

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

        findPreference("PREFERENCE_SERVER_ADDRESS").setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference.getKey().equals("PREFERENCE_SERVER_ADDRESS") ) {
            DisplayUtils.showProgressDialog(ServerSettingsActivity.this, getString(R.string.loading));
            mNetworkManager.callCheckServer((String) newValue);
        }
        return true;
    }

    private void openPreference( String key ) {
        PreferenceScreen screen = findPreferenceScreenForPreference( key, null );
        if( screen != null ) {
            screen.onItemClick(null, null, findPreference(key).getOrder(), 0);
        }
    }

    private PreferenceScreen findPreferenceScreenForPreference( String key, PreferenceScreen screen ) {
        if( screen == null ) {
            screen = getPreferenceScreen();
        }

        PreferenceScreen result = null;

        android.widget.Adapter ada = screen.getRootAdapter();
        for( int i = 0; i < ada.getCount(); i++ ) {
            String prefKey = ((Preference)ada.getItem(i)).getKey();
            if( prefKey != null && prefKey.equals( key ) ) {
                return screen;
            }
            if( ada.getItem(i).getClass().equals(android.preference.PreferenceScreen.class) ) {
                result = findPreferenceScreenForPreference( key, (PreferenceScreen) ada.getItem(i) );
                if( result != null ) {
                    return result;
                }
            }
        }

        return null;
    }

    private void manageResponse(boolean isValid) {
        DisplayUtils.dismissProgressDialog();
        if (isValid) {
            DisplayUtils.displayToast(ServerSettingsActivity.this, getString(R.string.first_use_valid_server));
        } else {
            DisplayUtils.displayToast(ServerSettingsActivity.this, getString(R.string.error_invalid_server));
            openPreference("PREFERENCE_SERVER_ADDRESS");
        }
    }


    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.CheckServerSuccess) {
                manageResponse(true);
            } else if (data == NetworkManager.NetworkResult.CheckServerError) {
                manageResponse(false);
            }
        }
    }
}
