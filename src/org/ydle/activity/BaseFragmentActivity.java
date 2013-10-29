package org.ydle.activity;

import org.ydle.R;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.ActivityUtils;

import roboguice.activity.RoboFragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

public class BaseFragmentActivity extends RoboFragmentActivity {

	@Inject
	protected SharedPreferences prefs;

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
		return ActivityUtils.getConf(prefs);
	}

}
