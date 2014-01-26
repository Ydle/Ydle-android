package org.ydle.activity.settings;

import org.ydle.R;
import org.ydle.activity.common.BaseFragmentActivity;
import org.ydle.fragment.settings.ExtraFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ExtraActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_host_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		ExtraFragment fragment = new ExtraFragment();
		Bundle arguments = new Bundle();
		///arguments.putParcelable(IntentConstantes.ITEM, getIntent()
		//		.getParcelableExtra(IntentConstantes.ITEM));
		fragment.setArguments(arguments);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment).commit();

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.menu_refresh);
        menu.removeItem(R.id.action_settings);
        menu.removeItem(R.id.action_add);
        return result;
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
