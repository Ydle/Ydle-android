package org.ydle.fragment.settings;

import org.ydle.IntentConstantes;
import org.ydle.R;
import org.ydle.activity.settings.HostDetailActivity;
import org.ydle.activity.settings.HostListActivity;
import org.ydle.model.configuration.ServeurInfo;
import org.ydle.utils.PreferenceUtils;

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
		FramgmentValidator<ServeurInfo> {

	private static final String TAG = "Ydle.HostDetailFragment";

	private String error = "";

	EditTextPreference ip;

	EditTextPreference nom;

	EditTextPreference port;

	EditTextPreference identifiant;

	EditTextPreference path;

	ServeurInfo mItem;

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

		boolean modeAvance = PreferenceUtils.getConf(getPreferenceManager()
				.getSharedPreferences()).avance;

		ip = ((EditTextPreference) findPreference("pref_ip"));
		nom = ((EditTextPreference) findPreference("pref_nom"));
		port = ((EditTextPreference) findPreference("pref_port"));
		identifiant = ((EditTextPreference) findPreference("pref_identifiant"));
		path = ((EditTextPreference) findPreference("pref_path"));

		path.setEnabled(modeAvance);

		if (getArguments() != null
				&& getArguments().containsKey(IntentConstantes.ITEM)) {
			mItem = getArguments().getParcelable(IntentConstantes.ITEM);
		} else {
			mItem = getActivity().getIntent().getParcelableExtra(
					IntentConstantes.ITEM);
		}

		if (mItem == null) {
			mItem = new ServeurInfo();
		}

		if (mItem != null) {
			port.setText(String.valueOf(mItem.port));
			ip.setText(mItem.host);
			identifiant.setText(mItem.identifiant);
			nom.setText(mItem.nom);
			path.setText(mItem.applicationName);
		} else {

			if (modeAvance) {
				path.setText("app_dev.php/");
			} else {
				path.setText("");
			}

		}

	}

	@Override
	public boolean isValide() {
		Log.d(TAG, "validate serveur info");
		error = "";

		if (identifiant.getText() == null || identifiant.getText().isEmpty()) {
			error = "Identifiant non Valide";
			return false;
		}
		return true;
	}

	@Override
	public String getError() {
		return error;
	}

	@Override
	public ServeurInfo getData() {
		ServeurInfo data = new ServeurInfo();
		data.port = Integer.valueOf(port.getText());
		data.identifiant = identifiant.getText();
		data.host = ip.getText();
		data.nom = nom.getText();
		data.applicationName = path.getText();
		data.actif = mItem.actif;

		return data;
	}

}
