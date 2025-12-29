import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
	enabled(enable: boolean): void;
	enableSecureView(imagePath: string): void;
	disableSecureView(): void;

	// Required methods for event emitter support
	addListener(eventName: string): void;
	removeListeners(count: number): void;
}

export default TurboModuleRegistry.get<Spec>('RNScreenshotPrevent');
