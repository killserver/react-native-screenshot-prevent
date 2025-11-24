
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#import "RCTConvert.h"
#import "RCTEventEmitter.h"
#else
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTEventEmitter.h>
#endif

#ifdef RCT_NEW_ARCH_ENABLED
#import <RNScreenshotPreventSpec/RNScreenshotPreventSpec.h>
#endif

@interface RNScreenshotPrevent : RCTEventEmitter <RCTBridgeModule
#ifdef RCT_NEW_ARCH_ENABLED
                                                  ,
                                                  NativeRNScreenshotPreventSpec
#endif
                                                  >

@end
