import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import { useEffect } from 'react';

type FN = (resp: any) => void
type Return = {
    readonly remove: () => void
}
let addListen: any, RNScreenshotPrevent: any;
if (Platform.OS !== "web") {
    const { RNScreenshotPrevent: RNScreenshotPreventNative } = NativeModules;
    RNScreenshotPrevent = {
        ...RNScreenshotPreventNative,
        enableSecureView: function enableSecureView(imagePath = "") {
            if (Platform.OS === "ios") {
                RNScreenshotPreventNative.enableSecureView(imagePath)
            } else {
                RNScreenshotPreventNative.enableSecureView()
            }
        }
    }
    const eventEmitter = new NativeEventEmitter(RNScreenshotPrevent);

    /**
     * subscribes to userDidTakeScreenshot event
     * @param {function} callback handler
     * @returns {function} unsubscribe fn
     */
    addListen = (fn: FN): Return => {
        if (typeof (fn) !== 'function') {
            console.error('RNScreenshotPrevent: addListener requires valid callback function');
            return {
                remove: (): void => {
                    console.error("RNScreenshotPrevent: remove not work because addListener requires valid callback function");
                }
            };
        }

        return eventEmitter.addListener("userDidTakeScreenshot", fn);
    }
} else {
    RNScreenshotPrevent = {
        enabled: (enabled: boolean): void => {
            console.warn("RNScreenshotPrevent: enabled not work in web. value: " + enabled);
        },
        enableSecureView: (): void => {
            console.warn("RNScreenshotPrevent: enableSecureView not work in web");
        },
        disableSecureView: (): void => {
            console.warn("RNScreenshotPrevent: disableSecureView not work in web");
        }
    }
    addListen = (fn: FN): Return => {
        if (typeof (fn) !== 'function') {
            console.error('RNScreenshotPrevent: addListener requires valid callback function');
            return {
                remove: (): void => {
                    console.error("RNScreenshotPrevent: remove not work because addListener requires valid callback function");
                }
            };
        }
        console.warn("RNScreenshotPrevent: addListener not work in web");
        return {
            remove: (): void => {
                console.warn("RNScreenshotPrevent: remove addListener not work in web");
            }
        }
    }
}

export const usePreventScreenshot = () => {
    useEffect(() => {
        RNScreenshotPrevent.enabled(true);
        return () => {
            RNScreenshotPrevent.enabled(false);
        };
    }, []);
}

export const useDisableSecureView = () => {
    useEffect(() => {
        RNScreenshotPrevent.enableSecureView();
        return () => {
            RNScreenshotPrevent.disableSecureView();
        };
    }, []);
}

export const enabled: (enabled: boolean) => void = RNScreenshotPrevent.enabled
export const enableSecureView: (imagePath?: string) => void = RNScreenshotPrevent.enableSecureView
export const disableSecureView: () => void = RNScreenshotPrevent.disableSecureView
export const addListener: (fn: FN) => void = addListen
export default RNScreenshotPrevent;
