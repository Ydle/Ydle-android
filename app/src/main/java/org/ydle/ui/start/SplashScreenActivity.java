package org.ydle.ui.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.ydle.BuildConfig;
import org.ydle.R;
import org.ydle.config.Constants;
import org.ydle.data.preferences.YdlePreferences;
import org.ydle.push_notification.GCMUtils;
import org.ydle.ui.DashboardActivity;
import org.ydle.ui.YdleActivity;

/**
 * Created by Jean-Baptiste on 03/03/14.
 */
public class SplashScreenActivity extends YdleActivity {

    private static final String LOG_TAG = SplashScreenActivity.class.getSimpleName();

    private TextView mVersionNumberTextView;
    private boolean mFirstUse = true;

    private static final int STOPSPLASH = 0;

    private Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    if(YdlePreferences.isFirstUse()) {
                        FirstUseActivity.displayFirstUseSettingsActivity(SplashScreenActivity.this);
                    } else {
                        DashboardActivity.displayDashboardActivity(SplashScreenActivity.this);
                    }
                    break;
            }
            finish();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        setContentView(R.layout.splashscreen_activity);

        mVersionNumberTextView = (TextView) findViewById(R.id.splashscreen_version_number_textview);
        mVersionNumberTextView.setText(BuildConfig.VERSION_NAME);

        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, Constants.SPLASH_TIME_DISPLAYED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!YdlePreferences.isFirstUse()) {
            mNetworkManager.callGetAllRooms();
            mNetworkManager.callGetAllRoomTypes();
        }
    }
}
