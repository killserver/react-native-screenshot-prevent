
package com.killserver.screenshotprev;

import android.view.WindowManager;

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
    if (_enable) {
      this.reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          reactContext.getCurrentActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
      });
    } else {
      this.reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          reactContext.getCurrentActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
      });
    }
  }

}