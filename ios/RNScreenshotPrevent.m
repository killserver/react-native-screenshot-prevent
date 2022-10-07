
#import "RNScreenshotPrevent.h"
#import "UIImage+ImageEffects.h"

@implementation RNScreenshotPrevent {
    BOOL enabled;
    UIImageView *obfuscatingView;
}

RCT_EXPORT_MODULE();
- (NSArray<NSString *> *)supportedEvents {
    return @[@"userDidTakeScreenshot"];
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

#pragma mark - Lifecycle

- (void) startObserving {
    NSNotificationCenter *center = [NSNotificationCenter defaultCenter];
    // handle inactive event
    [center addObserver:self selector:@selector(handleAppStateResignActive)
                            name:UIApplicationWillResignActiveNotification
                            object:nil];
    // handle active event
    [center addObserver:self selector:@selector(handleAppStateActive)
                            name:UIApplicationDidBecomeActiveNotification
                            object:nil];
    // handle screenshot taken event
    [center addObserver:self selector:@selector(handleAppScreenshotNotification)
                            name:UIApplicationUserDidTakeScreenshotNotification
                            object:nil];
}

- (void) stopObserving {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma mark - App Notification Methods

/** displays blurry view when app becomes inactive */
- (void)handleAppStateResignActive {
    if (self->enabled) {
        UIWindow    *keyWindow = [UIApplication sharedApplication].keyWindow;
        UIImageView *blurredScreenImageView = [[UIImageView alloc] initWithFrame:keyWindow.bounds];

        UIGraphicsBeginImageContext(keyWindow.bounds.size);
        [keyWindow drawViewHierarchyInRect:keyWindow.frame afterScreenUpdates:NO];
        UIImage *viewImage = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();

        blurredScreenImageView.image = [viewImage applyLightEffect];

        self->obfuscatingView = blurredScreenImageView;
        [[UIApplication sharedApplication].keyWindow addSubview:self->obfuscatingView];

    }
}

/** removes blurry view when app becomes active */
- (void)handleAppStateActive {
    if  (self->obfuscatingView) {
        [UIView animateWithDuration: 0.3
                         animations: ^ {
                             self->obfuscatingView.alpha = 0;
                         }
                         completion: ^(BOOL finished) {
                             [self->obfuscatingView removeFromSuperview];
                             self->obfuscatingView = nil;
                         }
         ];
    }
}

/** sends screenshot taken event into app */
- (void) handleAppScreenshotNotification {
    [self sendEventWithName:@"userDidTakeScreenshot" body:nil];
}

+(BOOL) requiresMainQueueSetup
{
  return YES;
}

#pragma mark - Public API

RCT_EXPORT_METHOD(enabled:(BOOL) _enable) {
    self->enabled = _enable;
}

@end
