package org.ydle.utils;

import java.net.URL;
import java.net.URLConnection;

public class ServerUtils {
	
	public static boolean isConnectedToServer(String url, int timeout) {
	    try{
	        URL myUrl = new URL(url);
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.connect();
	        return true;
	    } catch (Exception e) {
	        // Handle your exceptions
	        return false;
	    }
	}
	
	
}
