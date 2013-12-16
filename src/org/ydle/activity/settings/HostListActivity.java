package org.ydle.activity.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.wizard.WizardActivity;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.fragment.settings.HostListFragment;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.Callbacks;
import org.ydle.utils.ObjectSerializer;

import roboguice.activity.RoboFragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

public class HostListActivity extends RoboFragmentActivity implements
		Callbacks<ServeurInfo> {

	private static final String HOSTS = "host";
	private static final String TAG = "Ydle.HostListActivity";

	ServeurInfo current;

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
		setContentView(R.layout.activity_host_list);

		if (findViewById(R.id.host_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			HostListFragment hostListFragment = (HostListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.host_list);
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			hostListFragment.setActivateOnItemClick(true);

		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host, menu);
		menu.removeItem(R.id.menu_edit);
		menu.removeItem(R.id.menu_delete);
		menu.removeItem(R.id.menu_actif);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			onEdit(null);
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

	@SuppressWarnings("unchecked")
	public void addHost(ServeurInfo s) {
		List<ServeurInfo> currentTasks = null;
		try {
			currentTasks = (List) ObjectSerializer.deserialize(prefs
					.getStringSet(HOSTS, null));
		} catch (IOException e1) {
		}
		if (null == currentTasks) {
			currentTasks = new ArrayList<ServeurInfo>();
		}
		currentTasks.add(s);

		// save the task list to preference
		Editor editor = prefs.edit();
		try {
			editor.putStringSet(HOSTS, ObjectSerializer.serialize(currentTasks));
		} catch (IOException e) {
		}
		editor.commit();
	}

	public ServeurInfo getItemToDelete() {
		return getIntent().getParcelableExtra(IntentConstantes.DELETED_ITEM);
	}
}
