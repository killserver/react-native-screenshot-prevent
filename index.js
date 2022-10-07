import { NativeModules, NativeEventEmitter } from 'react-native';

const { RNScreenshotPrevent } = NativeModules;
const eventEmitter = new NativeEventEmitter(RNScreenshotPrevent);

/**
 * subscribes to userDidTakeScreenshot event
 * @param {function} callback handler
 * @returns {function} unsubscribe fn
 */
export const addListener = (fn) => {
    if(typeof(fn) !== 'function'){
        console.error('RNScreenshotPrevent: addListener requires valid callback function');
        return;
    }

    return eventEmitter.addListener("userDidTakeScreenshot", fn);
}

export default RNScreenshotPrevent;
