package org.ydle;

import android.app.Application;

//@ReportsCrashes(formKey = "", formUri = "http://www.yourselectedbackend.com/reportpath")
public class Ydle extends Application {

	@Override
	  public void onCreate() {
	    // The following line triggers the initialization of ACRA
	    super.onCreate();
	   // ACRA.init(this);
	  }
}
