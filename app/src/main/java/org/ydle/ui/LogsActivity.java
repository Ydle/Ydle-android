package org.ydle.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.data.memoryprovider.MemoryProvider;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.adapter.DashboardAdapter;
import org.ydle.ui.rooms.RoomsActivity;
import org.ydle.utils.DeviceInfoUtils;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class LogsActivity extends YdleActivity {

    private static final String LOG_TAG = LogsActivity.class.getSimpleName();

    public static void displayLogsActivity(final Activity from) {
        final Intent i = new Intent(from, LogsActivity.class);
        from.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs_activity);
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.GetAllLogsSuccess) {
                Log.d(LOG_TAG, "Les logs ont bien été chargés");
                Log.d(LOG_TAG, MemoryProvider.getInstance().getRooms().toString());
            } else if (data == NetworkManager.NetworkResult.GetAllLogsError) {
                Log.d(LOG_TAG, "Echec lors du téléchargement des logs");
            }
        }
    }
}
