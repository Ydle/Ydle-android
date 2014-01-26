package org.ydle.activity;

import org.ydle.R;
import org.ydle.activity.common.BaseActivity;
import org.ydle.activity.settings.HostListActivity;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {

	private static final String TAG = "Ydle.AboutActivity";

	@InjectView(R.id.version)
	TextView aboutVersion;
	@InjectView(R.id.link)
	TextView link;
	@InjectView(R.id.contact)
	TextView contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		aboutVersion.setText(getText(R.string.version) + getVersionNumber());
	}

	public void web(View view) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String
				.valueOf(getText(R.string.ydle_site)))));
	}

	/**
	 * Get current version number.
	 * 
	 * @return
	 */
	private String getVersionNumber() {
		String version = "?";
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			version = pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Package name not found", e);
		}
		;
		return version;
	}

	/**
	 * Get application name.
	 * 
	 * @return
	 */
	public String getApplicationName() {
		String name = "?";
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			name = getString(pi.applicationInfo.labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Package name not found", e);
		}
		;
		return name;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.removeItem(R.id.menu_refresh);
		menu.removeItem(R.id.action_settings);
		menu.removeItem(R.id.action_add);
		return result;
	}



}
