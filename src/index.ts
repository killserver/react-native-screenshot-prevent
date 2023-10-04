import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import { useEffect } from 'react';

type FN = (resp: any) => void;
type Return = {
    readonly remove: () => void;
};

let addListen: any, RNScreenshotPrevent: any;
if (Platform.OS !== 'web') {
    const { RNScreenshotPrevent: RNScreenshotPreventNative } = NativeModules;
    RNScreenshotPrevent = RNScreenshotPreventNative;
    const eventEmitter = new NativeEventEmitter(RNScreenshotPrevent);

    /**
     * subscribes to userDidTakeScreenshot event
     * @param {function} callback handler
     * @returns {function} unsubscribe fn
     */
    addListen = (fn: FN): Return => {
        if (typeof fn !== 'function') {
            console.error(
                'RNScreenshotPrevent: addListener requires valid callback function'
            );
            return {
                remove: (): void => {
                    console.error(
                        'RNScreenshotPrevent: remove not work because addListener requires valid callback function'
                    );
                },
            };
        }

        return eventEmitter.addListener('userDidTakeScreenshot', fn);
    };
} else {
    RNScreenshotPrevent = {
        enabled: (enabled: boolean): void => {
            console.warn(
                'RNScreenshotPrevent: enabled not work in web. value: ' +
                    enabled
            );
        },
        enableSecureView: (): void => {
            console.warn(
                'RNScreenshotPrevent: enableSecureView not work in web'
            );
        },
        disableSecureView: (): void => {
            console.warn(
                'RNScreenshotPrevent: disableSecureView not work in web'
            );
        },
    };
    addListen = (fn: FN): Return => {
        if (typeof fn !== 'function') {
            console.error(
                'RNScreenshotPrevent: addListener requires valid callback function'
            );
            return {
                remove: (): void => {
                    console.error(
                        'RNScreenshotPrevent: remove not work because addListener requires valid callback function'
                    );
                },
            };
        }
        console.warn('RNScreenshotPrevent: addListener not work in web');
        return {
            remove: (): void => {
                console.warn(
                    'RNScreenshotPrevent: remove addListener not work in web'
                );
            },
        };
    };
}

class RNScreenshotPreventSend {
    public static enabled(enabled: boolean): void {
        return RNScreenshotPrevent.enabled(enabled);
    }

    public static enableSecureView(): void {
        if (!__DEV__) {
            return;
        }
        RNScreenshotPrevent.enableSecureView();
    }

    public static disableSecureView(): void {
        if (!__DEV__) {
            return;
        }
        RNScreenshotPrevent.disableSecureView();
    }

    public static addListener(fn: FN): Return {
        return addListen(fn);
    }
}

export const usePreventScreenshot = () => {
    useEffect(() => {
        RNScreenshotPreventSend.enabled(true);
        return () => {
            RNScreenshotPreventSend.enabled(false);
        };
    }, []);
};

export const useDisableSecureView = () => {
    useEffect(() => {
        if (!__DEV__) {
            return;
        }
        RNScreenshotPreventSend.enableSecureView();
        return () => {
            RNScreenshotPreventSend.disableSecureView();
        };
    }, []);
};

export const enabled: (enabled: boolean) => void =
    RNScreenshotPreventSend.enabled;
export const enableSecureView: () => void =
    RNScreenshotPreventSend.enableSecureView;
export const disableSecureView: () => void =
    RNScreenshotPreventSend.disableSecureView;
export const addListener: (fn: FN) => Return = addListen;
export default RNScreenshotPreventSend;
