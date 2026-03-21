import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import { useEffect } from 'react';
import NativeScreenshotPrevent from './NativeRNScreenshotPrevent';

type FN = (resp: any) => void;
type Return = {
	readonly remove: () => void;
};

let addListen: any;
let RNScreenshotPrevent: any = null;

if (Platform.OS !== 'web') {
	// Try to use TurboModule first, fallback to legacy bridge
	const nativeModule =
		NativeScreenshotPrevent ?? NativeModules.RNScreenshotPrevent;

	if (nativeModule) {
		RNScreenshotPrevent = {
			...nativeModule,
			enableSecureView: (imagePath: string = ''): void => {
				nativeModule.enableSecureView(imagePath);
			},
		};
		const eventEmitter = new NativeEventEmitter(nativeModule);

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
		console.warn('RNScreenshotPrevent: Native module not found');
		RNScreenshotPrevent = {
			enabled: () => {},
			enableSecureView: () => {},
			disableSecureView: () => {},
		};
		addListen = () => ({ remove: () => {} });
	}
} else {
	RNScreenshotPrevent = {
		enabled: (enabled: boolean): void => {
			console.warn(
				'RNScreenshotPrevent: enabled not work in web. value: ',
				enabled
			);
		},
		enableSecureView: (imagePath: string = ''): void => {
			console.warn(
				'RNScreenshotPrevent: enableSecureView not work in web.',
				!!imagePath ? `send: ${imagePath}` : ''
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

export const usePreventScreenshot = () => {
	useEffect(() => {
		RNScreenshotPrevent?.enabled(true);
		return () => {
			RNScreenshotPrevent?.enabled(false);
		};
	}, []);
};

export const useDisableSecureView = (imagePath: string = '') => {
	useEffect(() => {
		RNScreenshotPrevent?.enableSecureView(imagePath);
		return () => {
			RNScreenshotPrevent?.disableSecureView();
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export const enabled: (enabled: boolean) => void = (enable) =>
	RNScreenshotPrevent?.enabled(enable);

export const enableSecureView: (imagePath?: string) => void = (imagePath) =>
	RNScreenshotPrevent?.enableSecureView(imagePath ?? '');

export const disableSecureView: () => void = () =>
	RNScreenshotPrevent?.disableSecureView();

export const addListener: (fn: FN) => Return = addListen;

export default RNScreenshotPrevent;
