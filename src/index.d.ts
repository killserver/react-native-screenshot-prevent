declare module 'react-native-screenshot-prevent' {
  export function enabled(value: boolean): void
  export function enableSecureView(): void;
  export function disableSecureView(): void;
  export function usePreventScreenshot(): void;
  export function useDisableSecureView(): void;
  export function addListener(fn: Function): void;
}