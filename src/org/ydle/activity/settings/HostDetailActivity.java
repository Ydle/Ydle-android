package org.ydle.activity.settings;

import org.ydle.R;
import org.ydle.activity.IntentConstantes;
import org.ydle.fragment.settings.HostDetailFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity representing a single Host detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link HostListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link HostDetailFragment}.
 */
public class HostDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		HostDetailFragment fragment = new HostDetailFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(IntentConstantes.ITEM, getIntent()
				.getParcelableExtra(IntentConstantes.ITEM));
		fragment.setArguments(arguments);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this,
					new Intent(this, HostListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
