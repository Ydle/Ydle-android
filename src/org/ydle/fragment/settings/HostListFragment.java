package org.ydle.fragment.settings;

import java.util.List;

import org.ydle.R;
import org.ydle.activity.common.BaseListFragment;
import org.ydle.activity.settings.HostListActivity;
import org.ydle.adapter.HostListAdapter;
import org.ydle.dummy.DummyContent;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.Callbacks;
import org.ydle.utils.PreferenceUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * A list fragment representing a list of Hosts. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link HostDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class HostListFragment extends BaseListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private static final String TAG = "Ydle.HostListFragment";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks<ServeurInfo> mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	private List<ServeurInfo> items;

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks<ServeurInfo> sDummyCallbacks = new Callbacks<ServeurInfo>() {
		@Override
		public void onItemSelected(ServeurInfo id, List<ServeurInfo> items) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public HostListFragment() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCallbacks = (Callbacks<ServeurInfo>) getActivity();

		ServeurInfo serverTodelete = ((HostListActivity) getActivity())
				.getItemToDelete();
		
		PreferenceUtils.deleteServeur(serverTodelete,prefs,getActivity());
		
		items = getConf().serversYdle;

		setListAdapter(new HostListAdapter(getActivity(), this.items));

	}

	private void onClick(View view, int position) {
		ServeurInfo serverInfo = DummyContent.ITEMS.get(position);
		Log.d(TAG, "position : " + position);
		if (previous != null) {
			Log.d(TAG, "previous : " + previous.getId());
			((ImageView) previous.findViewById(R.id.etat))
					.setBackgroundResource(R.drawable.voyant_rouge);
			previous.setSelected(false);
		}
		view.setSelected(true);
		previous = view;
		((ImageView) previous.findViewById(R.id.etat))
				.setBackgroundResource(R.drawable.voyant_vert);
		Log.d(TAG, "selection : ");
		// view.findViewById(id)

		mCallbacks.onItemSelected(serverInfo, getConf().serversYdle);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks<ServeurInfo>) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	View previous;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		if (items.get(position) != null) {
			mCallbacks.onItemSelected(items.get(position), items);
		}
	}
}
