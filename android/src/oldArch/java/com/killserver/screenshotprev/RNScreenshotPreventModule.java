package com.killserver.screenshotprev;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ImageView;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;
import java.net.URL;

public class RNScreenshotPreventModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private final ReactApplicationContext reactContext;
  private static RelativeLayout overlayLayout;
  private static boolean secureFlagWasSet;

  public RNScreenshotPreventModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.reactContext.addLifecycleEventListener(this);
  }

  @Override
  public String getName() {
    return "RNScreenshotPrevent";
  }

  @ReactMethod
  public void enabled(boolean _enable) {
    enabled(_enable, this.reactContext);
  }

  public static void enabled(boolean _enable, ReactApplicationContext reactContext) {
    if (reactContext.hasCurrentActivity()) {
      final Activity activity = reactContext.getCurrentActivity();
      if (activity != null) {
        if (_enable) {
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            }
          });
        } else {
          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            }
          });
        }
      }
    }
  }
  
  @ReactMethod
  public void enableSecureView(String imagePath) {
    enableSecureView(imagePath, this.reactContext);
  }

  public static void enableSecureView(String imagePath, ReactApplicationContext reactContext) {
    if (reactContext.hasCurrentActivity()) {
      final Activity activity = reactContext.getCurrentActivity();
      if (activity != null) {
        if (overlayLayout == null) {
          createOverlay(activity, imagePath);
        }
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
          }
        });
      }
    }
  }
  
  @ReactMethod
  public void disableSecureView() {
    disableSecureView(this.reactContext);
  }

  public static void disableSecureView(ReactApplicationContext reactContext) {
    if (reactContext.hasCurrentActivity()) {
      final Activity activity = reactContext.getCurrentActivity();
      if (activity != null) {
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (overlayLayout != null) {
              ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
              rootView.removeView(overlayLayout);
              overlayLayout = null;
            }
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
          }
        });
      }
    }
  }

  private static void createOverlay(Activity activity, String imagePath) {
    overlayLayout = new RelativeLayout(activity);
    overlayLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

    // Create an ImageView
    ImageView imageView = new ImageView(activity);
    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    imageParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

    imageView.setLayoutParams(imageParams);

    // Set image resource
    Bitmap bitmap = decodeImageUrl(imagePath);

    if (bitmap != null) {
      int imageHeight = (int)(bitmap.getHeight() * ((float) activity.getResources().getDisplayMetrics().widthPixels / bitmap.getWidth()));
      Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, activity.getResources().getDisplayMetrics().widthPixels, imageHeight, true);
      imageView.setImageBitmap(scaledBitmap);
    }

    overlayLayout.addView(imageView);
  }

  @Override
  public void onHostResume() {
    Activity currentActivity = this.reactContext.getCurrentActivity();
    if (currentActivity != null && overlayLayout != null) {
      currentActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ViewGroup rootView = (ViewGroup) currentActivity.getWindow().getDecorView().getRootView();
          rootView.removeView(overlayLayout);
          if (secureFlagWasSet) {
            currentActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            secureFlagWasSet = false;
          }
        }
      });
    }
  }

  @Override
  public void onHostPause() {
    Activity currentActivity = this.reactContext.getCurrentActivity();
    if (currentActivity != null && overlayLayout != null) {
      currentActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ViewGroup rootView = (ViewGroup) currentActivity.getWindow().getDecorView().getRootView();
          RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
          rootView.addView(overlayLayout, layoutParams);

          int flags = currentActivity.getWindow().getAttributes().flags;
          if ((flags & WindowManager.LayoutParams.FLAG_SECURE) != 0) {
            currentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            secureFlagWasSet = true;
          } else {
            secureFlagWasSet = false;
          }
        }
      });
    }
  }

  @Override
  public void onHostDestroy() {
    // Cleanup if needed
  }

  private static Bitmap decodeImageUrl(String imagePath) {
    try {
      URL imageUrl = new URL(imagePath);
      Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
      return bitmap;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}