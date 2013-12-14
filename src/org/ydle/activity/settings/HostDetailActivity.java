package org.ydle.activity.settings;

import org.ydle.R;
import org.ydle.activity.IntentConstantes;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.PreferenceUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

/**
 * An activity representing a single Host detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link HostListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link HostDetailFragment}.
 */
public class HostDetailActivity extends FragmentActivity {

	private static final String TAG = "Ydle.HostDetailActivity";
	@Inject
	protected SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		HostDetailFragment fragment = new HostDetailFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(IntentConstantes.ITEM, getItem());
		fragment.setArguments(arguments);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();

	}

	public ServeurInfo getItem() {
		return getIntent().getParcelableExtra(IntentConstantes.ITEM);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host, menu);
		menu.removeItem(R.id.action_add);
		menu.removeItem(R.id.menu_edit);
		if (getItem().actif) {
			menu.removeItem(R.id.menu_actif);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete:
			Intent intent = new Intent(this, HostListActivity.class);
			intent.putExtra(IntentConstantes.DELETED_ITEM,
					(Parcelable) getItem());

			startActivity(intent);
			finish();
			break;
		case R.id.menu_actif:
			PreferenceUtils.activeServer(getItem(), prefs);
			Log.d(TAG, "active host" + getItem().nom);
			startActivity(new Intent(this, HostListActivity.class));
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
