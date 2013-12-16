package org.ydle.utils;

import java.io.IOException;
import java.util.List;

import org.ydle.dummy.DummyContent;
import org.ydle.model.configuration.Configuration;
import org.ydle.model.configuration.ServeurInfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

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

	public static void updateFirstStart(SharedPreferences prefs) {
		Editor editor = prefs.edit().putBoolean("pref_firstStart", false);
		editor.apply();
		editor.commit();
	}

	public static void deleteServeur(ServeurInfo serverTodelete,
			SharedPreferences prefs, Activity activity) {

		Configuration conf = getConf(prefs);

		if (serverTodelete != null) {
			if (conf.serversYdle.size() == 1) {
				Toast.makeText(activity, "Impossible de supprimer le serveur",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity, "suppression du host",
						Toast.LENGTH_LONG).show();
				conf.serversYdle.remove(serverTodelete);
			}
		}

		Editor editor = prefs.edit();
		try {
			editor.putStringSet("host",
					ObjectSerializer.serialize(conf.serversYdle));
		} catch (IOException e) {
		}
		editor.commit();
	}

	public static void activeServer(ServeurInfo item, SharedPreferences prefs) {
		Configuration conf = getConf(prefs);

		List<ServeurInfo> servers = conf.serversYdle;

		for (ServeurInfo server : servers) {
			if (server.nom.equals(item.nom)) {
				server.actif = true;
			} else {
				server.actif = false;
			}
		}

		Editor editor = prefs.edit();
		try {
			editor.putStringSet("host", ObjectSerializer.serialize(servers));
		} catch (IOException e) {
		}
		editor.commit();

	}
	
	public static Configuration getConf(SharedPreferences prefs) {
		Configuration conf = new Configuration();
		conf.firstStart = prefs.getBoolean("pref_firstStart", true);
		conf.yanaApp = prefs.getBoolean("pref_yana", false);
		conf.sarahApp = prefs.getBoolean("pref_sarah", false);

		try {
			conf.serversYdle = (List) ObjectSerializer.deserialize(prefs
					.getStringSet("host", null));
		} catch (IOException e1) {
		}
		if (null == conf.serversYdle) {
			conf.serversYdle = DummyContent.ITEMS;
		}

		return conf;
	}
}
