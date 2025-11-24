package com.killserver.screenshotprev;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ScreenshotPreventTurboPackage extends TurboReactPackage {

    @Nullable
    @Override
    public NativeModule getModule(String name, @Nonnull ReactApplicationContext reactContext) {
        if (name.equals(ScreenshotPreventModule.NAME)) {
            return new ScreenshotPreventModule(reactContext);
        } else {
            return null;
        }
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return () -> {
            final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
            boolean isTurboModule = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
            moduleInfos.put(
                    ScreenshotPreventModule.NAME,
                    new ReactModuleInfo(
                            ScreenshotPreventModule.NAME,
                            ScreenshotPreventModule.class.getName(),
                            false, // canOverrideExistingModule
                            false, // needsEagerInit
                            true, // hasConstants
                            false, // isCxxModule
                            isTurboModule // isTurboModule
                    ));
            return moduleInfos;
        };
    }
}