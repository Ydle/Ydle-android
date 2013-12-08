package org.ydle.activity;

import java.util.ArrayList;
import java.util.List;

import org.ydle.R;
import org.ydle.fragment.RoomDetailFragment;
import org.ydle.fragment.RoomListFragment;
import org.ydle.model.Room;
import org.ydle.utils.Callbacks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

public class RoomListActivity extends BaseFragmentActivity implements
		Callbacks<Room> {

	private static final String TAG = "Ydle.RoomListActivity";
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_list);

		if (findViewById(R.id.room_detail_container) != null) {
			// Affiche du mode liste et détails des pièces pour les tablettes
			// lorsque l'affichage en horizontal
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				mTwoPane = true;

				// In two-pane mode, list items should be given the
				// 'activated' state when touched.
				((RoomListFragment) getSupportFragmentManager()
						.findFragmentById(R.id.room_list))
						.setActivateOnItemClick(true);
			}
		}
	}

	/**
	 * Callback method from {@link RoomListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Room id, List<Room> items) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putParcelable(IntentConstantes.ITEM, id);
			RoomDetailFragment fragment = new RoomDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.room_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, RoomPagedActivity.class);
			detailIntent.putExtra(IntentConstantes.ITEM, (Parcelable) id);
			detailIntent.putExtra(IntentConstantes.ITEMS,
					(ArrayList<Room>) items);

			int index = items.indexOf(id);
			Log.d(TAG, "index : " + index);
			detailIntent.putExtra(IntentConstantes.INDEX, index);
			startActivity(detailIntent);
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_refresh:
			((RoomListFragment) getSupportFragmentManager().findFragmentById(
					R.id.room_list)).refreshList();
			break;

		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}
}
