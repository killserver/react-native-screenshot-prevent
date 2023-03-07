import { NativeModules, NativeEventEmitter, Platform } from 'react-native';

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
    addListen = (fn) => {
        if(typeof(fn) !== 'function'){
            console.error('RNScreenshotPrevent: addListener requires valid callback function');
            return {
                remove: () => {
                    console.error("RNScreenshotPrevent: remove not work because addListener requires valid callback function");
                }
            };
        }

        return eventEmitter.addListener("userDidTakeScreenshot", fn);
    }
} else {
    RNScreenshotPrevent = {
        enabled: (enabled) => {
            console.warn("RNScreenshotPrevent: enabled not work in web");
        },
        enableSecureView: () => {
            console.warn("RNScreenshotPrevent: enableSecureView not work in web");
        },
        disableSecureView: () => {
            console.warn("RNScreenshotPrevent: disableSecureView not work in web");
        }
    }
    addListen = (fn) => {
        if(typeof(fn) !== 'function'){
            console.error('RNScreenshotPrevent: addListener requires valid callback function');
            return {
                remove: () => {
                    console.error("RNScreenshotPrevent: remove not work because addListener requires valid callback function");
                }
            };
        }
        console.warn("RNScreenshotPrevent: addListener not work in web");
        return {
            remove: () => {
                console.warn("RNScreenshotPrevent: remove addListener not work in web");
            }
        }
    }
}

export const addListener = addListen
export default RNScreenshotPrevent;
