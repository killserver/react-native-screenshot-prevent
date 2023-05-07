import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import { useEffect } from 'react';

let addListen, RNScreenshotPrevent;
if(Platform.OS !== "web") {
    const { RNScreenshotPrevent: RNScreenshotPreventNative } = NativeModules;
    RNScreenshotPrevent = RNScreenshotPreventNative
    const eventEmitter = new NativeEventEmitter(RNScreenshotPrevent);

    /**
     * subscribes to userDidTakeScreenshot event
     * @param {function} callback handler
     * @returns {function} unsubscribe fn
     */
    addListen = (fn): void => {
        if(typeof(fn) !== 'function') {
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
            console.warn("RNScreenshotPrevent: enabled not work in web");
        },
        enableSecureView: (): void => {
            console.warn("RNScreenshotPrevent: enableSecureView not work in web");
        },
        disableSecureView: (): void => {
            console.warn("RNScreenshotPrevent: disableSecureView not work in web");
        }
    }
    addListen = (fn): void => {
        if(typeof(fn) !== 'function') {
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

export const addListener = addListen
export default RNScreenshotPrevent;
