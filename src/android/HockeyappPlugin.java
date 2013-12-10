package net.netconomy.phonegap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import android.app.Application;
import android.content.res.XmlResourceParser;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class HockeyappPlugin extends CordovaPlugin {
    private String HOCKEY_CONFIG_KEY = "net.netconomy.phoengap.hockeyapp"; 
    private String APP_ID = "";
    private CordovaInterface _cordova;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        parseConfig(cordova.getActivity().getApplication());
        this._cordova = cordova;
        checkForUpdates();
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        checkForCrashes();
    }

    private void checkForCrashes() {
       CrashManager.register(this._cordova.getActivity(), APP_ID);
     }

     private void checkForUpdates() {
       // Remove this for store builds!
       UpdateManager.register(this._cordova.getActivity(), APP_ID);
     }
     
     private void parseConfig(Application application) {
         int id = application.getResources().getIdentifier("config", "xml", application.getPackageName());
         if (id == 0) {
             return;
         }

         XmlResourceParser xml = application.getResources().getXml(id);

         int eventType = -1;
         while (eventType != XmlResourceParser.END_DOCUMENT) {

             if (eventType == XmlResourceParser.START_TAG) {
                 if (xml.getName().equals("preference")) {
                     String name = xml.getAttributeValue(null, "name").toLowerCase();
                     String value = xml.getAttributeValue(null, "value");

                     if (name.equalsIgnoreCase(HOCKEY_CONFIG_KEY) && value != null) {
                         APP_ID = value;
                     }
                 }
             }

             try {
                 eventType = xml.next();
             } catch (Exception e) {
                 
             }
         }
     }

}