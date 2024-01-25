
package com.killserver.screenshotprev;

import android.view.WindowManager;

import android.app.Activity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNScreenshotPreventModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNScreenshotPreventModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNScreenshotPrevent";
  }

  @ReactMethod
  public void enabled(boolean _enable) {
    if (this.reactContext.hasCurrentActivity()) {
      final Activity activity = this.reactContext.getCurrentActivity();
      if (activity != null) {
        if (_enable) {
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            }
          });
        } else {
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            }
          });
        }
      }
    }
  }
  
  @ReactMethod
  public void enableSecureView() {
    if (this.reactContext.hasCurrentActivity()) {
      final Activity activity = this.reactContext.getCurrentActivity();
      if (activity != null) {
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
          }
        });
      }
    }
  }
  
  @ReactMethod
  public void disableSecureView() {
    if (this.reactContext.hasCurrentActivity()) {
      final Activity activity = this.reactContext.getCurrentActivity();
      if (activity != null) {
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
          }
        });
      }
    }
  }

  // Required for rn built in EventEmitter Calls.
  @ReactMethod
  public void addListener(String eventName) {

  }

  @ReactMethod
  public void removeListeners(Integer count) {

  }
}