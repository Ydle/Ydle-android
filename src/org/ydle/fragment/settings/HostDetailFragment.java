package org.ydle.fragment.settings;

import org.ydle.R;
import org.ydle.activity.settings.HostDetailActivity;
import org.ydle.activity.settings.HostListActivity;
import org.ydle.model.configuration.ServeurInfo;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * A fragment representing a single Host detail screen. This fragment is either
 * contained in a {@link HostListActivity} in two-pane mode (on tablets) or a
 * {@link HostDetailActivity} on handsets.
 */
public class HostDetailFragment extends PreferenceFragment implements
		FramgmentValidator {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	private static final String TAG = "Ydle.HostDetailFragment";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private ServeurInfo mItem;

	private String error="";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public HostDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.pref_host);

		if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
			mItem = getArguments().getParcelable(ARG_ITEM_ID);

			EditTextPreference ip = ((EditTextPreference) findPreference("pref_ip"));

			EditTextPreference nom = ((EditTextPreference) findPreference("pref_nom"));

			EditTextPreference port = ((EditTextPreference) findPreference("pref_port"));

			EditTextPreference identifiant = ((EditTextPreference) findPreference("pref_identifiant"));

			if (mItem != null) {
				port.setText(String.valueOf(mItem.port));
				ip.setText(mItem.host);
				identifiant.setText(mItem.identifiant);
				nom.setText(mItem.nom);
			}
		}
	}

	@Override
	public boolean isValide() {
		Log.d(TAG, "validate serveur info");
		error="";
		EditTextPreference identifiant = ((EditTextPreference) findPreference("pref_identifiant"));

		if (identifiant.getText() == null || identifiant.getText().isEmpty()) {
			error="Identifiant non Valide";
			return false;
		}
		return true;
	}

	@Override
	public String getError() {
		return error;
	}
	
	

}
