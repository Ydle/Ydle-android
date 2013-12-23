package org.ydle.activity.settings;

import java.util.List;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.wizard.WizardActivity;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.fragment.settings.HostListFragment;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.Callbacks;
import org.ydle.utils.PreferenceUtils;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

public class HostListActivity extends RoboFragmentActivity implements
		Callbacks<ServeurInfo>,
		SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String TAG = "Ydle.HostListActivity";

	ServeurInfo current;

	@InjectFragment(R.id.host_list)
	HostListFragment hostListFragment;

	private HostDetailFragment hostDetailsFragment;

	@Inject
	protected SharedPreferences prefs;

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_host_list);
		// activity_host_twopane.xml
		if (findViewById(R.id.host_detail_container) != null) {
			
			mTwoPane = true;
			hostDetailsFragment = (HostDetailFragment) getFragmentManager()
					.findFragmentById(R.id.host_detail_container);

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			hostListFragment.setActivateOnItemClick(true);

		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mTwoPane) {
			prefs.registerOnSharedPreferenceChangeListener(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mTwoPane) {
			prefs.unregisterOnSharedPreferenceChangeListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host, menu);
		if (!mTwoPane) {

			menu.removeItem(R.id.menu_delete);
			menu.removeItem(R.id.menu_actif);
		}
		menu.removeItem(R.id.menu_edit);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			onEdit(null);
			break;
		case R.id.menu_delete:
			if (current != null) {
				Intent intent = new Intent(this, HostListActivity.class);
				intent.putExtra(IntentConstantes.DELETED_ITEM,
						(Parcelable) current);

				startActivity(intent);
				finish();
			}
			break;
		case R.id.menu_actif:
			if (current != null) {
				PreferenceUtils.activeServer(current, prefs);
				Log.d(TAG, "active host" + current.nom);
				Intent intent = new Intent(this, HostListActivity.class);
				intent.putExtra(IntentConstantes.ITEM, (Parcelable) current);
				startActivity(intent);
				finish();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Callback method from {@link HostListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(ServeurInfo id, List<ServeurInfo> items) {
		Log.d(TAG, "onItemSelected " + id);
		current = id;
		onEdit(current);
	}

	public void onEdit(ServeurInfo id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putParcelable(IntentConstantes.ITEM, id);
			HostDetailFragment fragment = new HostDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.host_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent intent = new Intent(this, WizardActivity.class);
			intent.putExtra(IntentConstantes.ACTION, "host");
			Log.d(TAG, "onEdit : " + id);
			if (id != null) {
				intent.putExtra(IntentConstantes.ITEM, (Parcelable) id);
			}
			startActivity(intent);
			finish();
		}
	}

	public ServeurInfo getItemToDelete() {
		return getIntent().getParcelableExtra(IntentConstantes.DELETED_ITEM);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d(TAG, "HostListActivity.onSharedPreferenceChanged : " + key);
		if (mTwoPane) {

			hostDetailsFragment = (HostDetailFragment) getFragmentManager()
					.findFragmentById(R.id.host_detail_container);

			if (hostDetailsFragment != null && hostDetailsFragment.isInit()) {
				ServeurInfo server = hostDetailsFragment.getData();

				//if (!current.equals(server)) {

					Log.d(TAG, "ITEM " + current + " new serveur " + server);
					PreferenceUtils.updateServeur(server, current, prefs);
					Intent intent = new Intent(this, HostListActivity.class);
					intent.putExtra(IntentConstantes.ITEM, (Parcelable) server);

					startActivity(intent);

					finish();
				//}
			}
		}
	}
}
