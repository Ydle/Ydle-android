package org.ydle.fragment.wizard;

import org.ydle.activity.common.BaseFragment;
import org.ydle.fragment.settings.FramgmentValidator;
import org.ydle.utils.ActivityUtils;
import org.ydle.utils.ServerUtils;

import android.os.Bundle;

public class WizardStatusFragment extends BaseFragment implements
		FramgmentValidator<Object> {
	
	private boolean network;
	
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
			
			String url= getConf().getServer().getUrl();
			
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

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
