package org.ydle.activity.common;

import org.ydle.R;
import org.ydle.activity.settings.SettingsActivity;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.PreferenceUtils;

import roboguice.activity.RoboActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

public class BaseActivity extends RoboActivity {

	public static final String ACTION_LOGOUT = "org.ydle.LOGOUT";

	protected BroadcastReceiver receiver;
	@Inject
	protected SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				finish();
			}
		};

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_LOGOUT);
		registerReceiver(receiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivityForResult(new Intent(this, SettingsActivity.class), 1);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public Configuration getConf() {
		return PreferenceUtils.getConf(prefs);
	}
}
