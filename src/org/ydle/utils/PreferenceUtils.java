package org.ydle.utils;

import java.io.IOException;
import java.util.List;

import org.ydle.model.configuration.ServeurInfo;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class PreferenceUtils {

	private static final String TAG = "Ydle.PreferenceUtils";

	public static void updateServeur(ServeurInfo newServer,
			ServeurInfo editServeur, SharedPreferences prefs) {

		List<ServeurInfo> servers = null;
		try {
			servers = (List) ObjectSerializer.deserialize(prefs.getStringSet(
					"host", null));
		} catch (IOException e1) {
		}
		Log.d(TAG, "size" + servers.size());

		if (editServeur != null) {
			servers.remove(editServeur);
		}
		Log.d(TAG, "size" + servers.size());
		servers.add(newServer);
		Log.d(TAG, "size" + servers.size());

		Editor editor = prefs.edit();
		try {
			editor.putStringSet("host", ObjectSerializer.serialize(servers));
		} catch (IOException e) {
		}
		editor.commit();

	}
}
