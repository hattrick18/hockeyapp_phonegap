package net.netconomy.phonegap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class HockeyappPlugin extends CordovaPlugin {
    private static String APP_ID = "1daef6208b0180c53283c1b28a167e24";
    private static CordovaInterface _cordova;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
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

}