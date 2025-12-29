package com.killserver.screenshotprev;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNScreenshotPreventModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private final ReactApplicationContext reactContext;

  public RNScreenshotPreventModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.reactContext.addLifecycleEventListener(this);
  }

  @Override
  public String getName() {
    return ScreenshotPreventImpl.NAME;
  }

  @ReactMethod
  public void enabled(boolean _enable) {
    ScreenshotPreventImpl.enabled(_enable, this.reactContext);
  }
  
  @ReactMethod
  public void enableSecureView(String imagePath) {
    ScreenshotPreventImpl.enableSecureView(imagePath, this.reactContext);
  }
  
  @ReactMethod
  public void disableSecureView() {
    ScreenshotPreventImpl.disableSecureView(this.reactContext);
  }

  @Override
  public void onHostResume() {
    ScreenshotPreventImpl.onHostResume(this.reactContext);
  }

  @Override
  public void onHostPause() {
    ScreenshotPreventImpl.onHostPause(this.reactContext);
  }

  @Override
  public void onHostDestroy() {
    // Cleanup if needed
  }
}