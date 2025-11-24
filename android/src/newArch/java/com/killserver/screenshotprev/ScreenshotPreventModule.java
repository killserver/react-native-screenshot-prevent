package com.killserver.screenshotprev;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = ScreenshotPreventModule.NAME)
public class ScreenshotPreventModule extends NativeRNScreenshotPreventSpec {

    public static final String NAME = "RNScreenshotPrevent";

    public ScreenshotPreventModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @Override
    public void enabled(boolean enable) {
        ScreenshotPreventImpl.enabled(enable, getReactApplicationContext());
    }

    @Override
    public void enableSecureView(String imagePath) {
        ScreenshotPreventImpl.enableSecureView(imagePath, getReactApplicationContext());
    }

    @Override
    public void disableSecureView() {
        ScreenshotPreventImpl.disableSecureView(getReactApplicationContext());
    }

    @Override
    public void addListener(String eventName) {
        // Set up any upstream listeners or background tasks
    }

    @Override
    public void removeListeners(double count) {
        // Remove any upstream listeners or background tasks
    }
}