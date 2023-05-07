import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  enabled(enabled: boolean, callback: (val: boolean) => void) => void;
  enableSecureView() => void;
  disableSecureView() => void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('ScreenshotPrevent');
