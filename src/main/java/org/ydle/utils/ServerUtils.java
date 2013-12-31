package org.ydle.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class ServerUtils {
	
	private static final int CONNECTION_TIMEOUT = 20000;
    private static DefaultHttpClient httpclient = getHttpClient();
    private static final String TAG = "Ydle.ServerUtils";
    
    public static DefaultHttpClient getHttpClient() {
        if (httpclient == null) {
            HttpParams myHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(myHttpParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(myHttpParams, CONNECTION_TIMEOUT);
            return new DefaultHttpClient(myHttpParams);
        } else {
            return httpclient;
        }
    }
	
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
	
	
	public static String get(String auth, String url, Param... params) {
        // URLConnection connection;
        HttpGet httpget = new HttpGet();
        Log.d(TAG, "get url : "+url);
        HttpResponse response;
        try {

            BasicHttpParams httpParams = new BasicHttpParams();
            for (Param param : params) {
                httpParams.setParameter(param.key, param.value);
            }
            httpget.setParams(httpParams);

            httpget.addHeader("Cookie", auth);

            httpget.setURI(new URI("http://"+url));
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                instream.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            httpget.abort();
            Log.w(TAG, "There was a protocol based error", e);
        } catch (IOException e) {
            httpget.abort();
            Log.w(TAG, "There was an IO Stream related error", e);
        } catch (URISyntaxException e) {
            httpget.abort();
            Log.w(TAG, "There was an IO Stream related error", e);
        }
        return null;
    }
	
	  public static String convertStreamToString(InputStream is) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	  
	  public static class Param {
	        public String key;
	        public Object value;

	        public Param(String key, Object value) {
	            this.key = key;
	            this.value = value;
	        }
	    }
	
}
