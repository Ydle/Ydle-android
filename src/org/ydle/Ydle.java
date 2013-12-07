package org.ydle;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;


//@ReportsCrashes(
//		formKey = "",
//        formUri = "https://sguernion.cloudant.com/acra-storage/_design/acra-storage/_update/report",
//        reportType = org.acra.sender.HttpSender.Type.JSON,
//        httpMethod = org.acra.sender.HttpSender.Method.PUT,
//        formUriBasicAuthLogin="bothastrastandishoustarr",
//        formUriBasicAuthPassword="I2eIcIgDFlveY6qoY3slfnTO"
//)
public class Ydle extends Application {

	@Override
	  public void onCreate() {
	    // The following line triggers the initialization of ACRA
	    super.onCreate();
	    ACRA.init(this);
	  }
}
