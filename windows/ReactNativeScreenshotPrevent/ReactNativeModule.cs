using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Microsoft.ReactNative;
using Microsoft.ReactNative.Managed;

namespace ReactNativeScreenshotPrevent
{
    [ReactModule("ReactNativeScreenshotPrevent")]
    internal sealed class ReactNativeModule
    {
        // See https://microsoft.github.io/react-native-windows/docs/native-modules for details on writing native modules

        private ReactContext _reactContext;

        [ReactInitializer]
        public void Initialize(ReactContext reactContext)
        {
            _reactContext = reactContext;
        }

        [ReactMethod]
        public void enabled(bool _enable, Action<bool> callback)
        {
            Windows.UI.ViewManagement.ApplicationView.GetForCurrentView().IsScreenCaptureEnabled = !_enable;
            callback(true);
            userDidTakeScreenshot(_enable);
        }

        [ReactMethod]
        public void enableSecureView()
        {
            userDidTakeScreenshot(true);
            Windows.UI.ViewManagement.ApplicationView.GetForCurrentView().IsScreenCaptureEnabled = false;
        }

        [ReactMethod]
        public void disableSecureView()
        {
            userDidTakeScreenshot(false);
            Windows.UI.ViewManagement.ApplicationView.GetForCurrentView().IsScreenCaptureEnabled = true;
        }

        [ReactEvent]
        public ReactEvent<bool> userDidTakeScreenshot { get; set; }
    }
}
