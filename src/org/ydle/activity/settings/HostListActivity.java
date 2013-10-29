package org.ydle.activity.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ydle.R;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.fragment.settings.HostListFragment;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.Callbacks;
import org.ydle.utils.ObjectSerializer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * An activity representing a list of Hosts. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link HostDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HostListFragment} and the item details (if present) is a
 * {@link HostDetailFragment}.
 * <p>
 * This activity also implements the required {@link HostListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class HostListActivity extends FragmentActivity implements
		Callbacks<ServeurInfo> {

	private static final String SHARED_PREFS_FILE = "ydle_pref";
	private static final String HOSTS = "host";
	
	ServeurInfo current;

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
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			current = new ServeurInfo(null);
			onEdit(current);
			break;
		case R.id.action_delete:
			Toast.makeText(this, "suppression du host", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_edit:
			onEdit(current);
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
	public void onItemSelected(ServeurInfo id,List<ServeurInfo> items) {
		current = id;
	}

	public void onEdit(ServeurInfo id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putParcelable(HostDetailFragment.ARG_ITEM_ID, id);
			HostDetailFragment fragment = new HostDetailFragment();
			fragment.setArguments(arguments);

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, HostDetailActivity.class);
			detailIntent.putExtra(HostDetailFragment.ARG_ITEM_ID,
					(Parcelable) id);
			startActivity(detailIntent);
		}
	}

	public void addHost(ServeurInfo s) {
		List<ServeurInfo> currentTasks = null;
		if (null == currentTasks) {
			currentTasks = new ArrayList<ServeurInfo>();
		}
		currentTasks.add(s);

		// save the task list to preference
		SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		try {
			editor.putStringSet(HOSTS, ObjectSerializer.serialize(currentTasks));
		} catch (IOException e) {
		}
		editor.commit();
	}
}
