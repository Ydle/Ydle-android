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
import org.ydle.push_notification.GCMUtils;
import org.ydle.ui.adapter.DashboardAdapter;
import org.ydle.ui.rooms.RoomsActivity;
import org.ydle.utils.DeviceInfoUtils;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class DashboardActivity extends YdleActivity {

    private static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    private GridView mDashboardGridView;

    public static void displayDashboardActivity(final Activity from) {
        final Intent i = new Intent(from, DashboardActivity.class);
        from.startActivity(i);
        from.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        if(YdlePreferences.isFirstUse()) {
            DisplayUtils.displayToast(DashboardActivity.this, "TODO: Bienvenue");
            YdlePreferences.setFirstUse(false);
        }

        GCMUtils.register(this);

        mDashboardGridView = (GridView) findViewById(R.id.dashboard_gridview);
        int width = DeviceInfoUtils.getWidthScreenPx(this.getApplicationContext()) / 2 - 20;
        mDashboardGridView.setColumnWidth(width);
        final DashboardAdapter dashboardAdapter = new DashboardAdapter(this);
        mDashboardGridView.setAdapter(dashboardAdapter);

        mDashboardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                DashboardAdapter.Item item = (DashboardAdapter.Item) dashboardAdapter.getItem(position);
                Toast.makeText(DashboardActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                DashboardAdapter.ItemType type = item.getType();
                if (type == DashboardAdapter.ItemType.ROOMS) {
                    RoomsActivity.displayRoomsActivity(DashboardActivity.this, false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNetworkManager.callGetAllRooms();
        mNetworkManager.callGetAllRoomTypes();
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.GetAllRoomsSuccess) {
                Log.d(LOG_TAG, "Data available");
                Log.d(LOG_TAG, MemoryProvider.getInstance().getRooms().toString());
            } else if (data == NetworkManager.NetworkResult.GetAllRoomsError) {
                Log.d(LOG_TAG, "Echec de la requête");
            }
        }
    }
}
