package org.ydle.model.configuration;

import java.util.ArrayList;
import java.util.List;


public class Configuration {
	
	public boolean graph;
	
	public boolean firstStart;
	
	public boolean yanaApp;
	
	public List<ServeurInfo> serversYdle = new ArrayList<ServeurInfo>();

	public boolean sarahApp;
	
	public ServeurInfo getServer() {
		if(serversYdle.size() == 1){
			return serversYdle.get(0);
		}else{
			for(ServeurInfo server : serversYdle){
				if(server.actif){
					return server;
				}
			}
		}
		return new ServeurInfo();
	}
}
