package clearCacheAndroid;

import android.content.Context;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class clearCacheAndroid extends CordovaPlugin {

    // Method used to empty cache data directory
    void deleteRecursive(File dir) {

        Log.d("DeleteRecursive", "DELETEPREVIOUS TOP" + dir.getPath());
        if (dir.isDirectory()) {

            String[] children = dir.list();
            for (String child : children) {
                File temp = new File(dir, child);
                if (temp.isDirectory()) {
                    Log.d("DeleteRecursive", "Recursive Call" + temp.getPath());
                    deleteRecursive(temp);
                } else {
                    Log.d("DeleteRecursive", "Delete File" + temp.getPath());
                    boolean b = temp.delete();
                    if (!b) {
                        Log.d("DeleteRecursive", "DELETE FAIL");
                    }
                }
            }
        }
        dir.delete();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("clearCache")) {
            // Get App context
            Context context = this.cordova.getActivity().getApplicationContext();
            // Use Cordova Executor Service
            cordova.getThreadPool().execute(() -> {

                File cacheDir = context.getCacheDir();
                File[] files = cacheDir.listFiles();

                long size = 0;
                    
                if (files != null) {
                    for (File file : files) {
                        // Delete cached data file content
                        deleteRecursive(file);
                        size = size + file.length();
                        // Thread-safe
                        callbackContext.success("Cache " + size);
                    }
                }
            });
            return true;
        }
        return false;
    }

    private void clearCache(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}