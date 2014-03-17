package org.ydle.ui.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ydle.BuildConfig;
import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.config.Constants;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.DashboardActivity;
import org.ydle.ui.YdleActivity;
import org.ydle.utils.AnimationUtils;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class FirstUseActivity extends YdleActivity {

    private static final String LOG_TAG = FirstUseActivity.class.getSimpleName();

    private EditText mNameEditText;
    private EditText mHostEditText;
    private Button mButton;

    public static void displayFirstUseSettingsActivity(final Activity from) {
        final Intent i = new Intent(from, FirstUseActivity.class);
        from.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_use_activity);

        mNameEditText = (EditText) findViewById(R.id.first_use_name_edittext);
        mNameEditText.setText(YdlePreferences.getServerName());
        mHostEditText = (EditText) findViewById(R.id.first_use_host_edittext);
        mHostEditText.setText(YdlePreferences.getServerAddress());
        mButton = (Button) findViewById(R.id.first_use_submit_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayUtils.showProgressDialog(FirstUseActivity.this, getString(R.string.loading));
                YdlePreferences.setServerName(mNameEditText.getText().toString());
                YdlePreferences.setServerAddress(mHostEditText.getText().toString());
                mNetworkManager.callGetAllRooms();
            }
        });
    }

    private void manageResponse(boolean isValid) {
        DisplayUtils.dismissProgressDialog();
        if (isValid) {
            YdlePreferences.setFirstUse(false);
            DisplayUtils.displayToast(FirstUseActivity.this, getString(R.string.first_use_valid_server));
            DashboardActivity.displayDashboardActivity(FirstUseActivity.this);
        } else {
            DisplayUtils.displayToast(FirstUseActivity.this, getString(R.string.error_invalid_server));
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