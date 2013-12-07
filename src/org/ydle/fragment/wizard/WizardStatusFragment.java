package org.ydle.fragment.wizard;

import org.ydle.fragment.settings.FramgmentValidator;
import org.ydle.model.configuration.Configuration;
import org.ydle.utils.ActivityUtils;
import org.ydle.utils.ServerUtils;

import roboguice.fragment.RoboFragment;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.inject.Inject;

public class WizardStatusFragment extends RoboFragment implements
		FramgmentValidator {
	
	private boolean network;
	
	@Inject
	protected SharedPreferences prefs;

	public WizardStatusFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO display activity status

		if (ActivityUtils.iswifiEnable(getActivity())) {
			// TODO test du wifi
			network = true;
		}else{
			if (ActivityUtils.isNetworkAvailable(getActivity())) {
				// TODO test d'internet si pas de wifi
				network = true;
			}else{
				//TODO aucune connexion
				network = false;
			}
		}

		if(network){
			
			Configuration conf = ActivityUtils.getConf(prefs);
			
			String url=conf.serveur.getUrl();
			
			if(ServerUtils.isConnectedToServer(url, 10)){
				// TODO test la connexion au serveur Ydle
			}
		}

		
	}

	@Override
	public boolean isValide() {
		return true;
	}

	@Override
	public String getError() {
		return "";
	}

}
