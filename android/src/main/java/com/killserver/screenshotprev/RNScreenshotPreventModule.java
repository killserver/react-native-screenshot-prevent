package com.killserver.screenshotprev;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.net.MalformedURLException;
import java.util.List;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.dialog.AlertFragment;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class RNScreenshotPreventModule extends ReactContextBaseJavaModule implements  ViewTreeObserver.OnWindowFocusChangeListener {
  private Dialog securedDialog;

  public RNScreenshotPreventModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @NonNull
  @Override
  public String getName() {
    return "RNScreenshotPrevent";
  }

  @ReactMethod
  public void enabled(boolean enable) {
    Log.i("RNScreenshotPreventModule", "secured called " + enable);

    final Activity activity = this.getReactApplicationContext().getCurrentActivity();
    if (!this.getReactApplicationContext().hasCurrentActivity() || activity == null) {
      return;
    }

    if (enable) {
      activity.runOnUiThread(() -> {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );
      });
    } else {
      activity.runOnUiThread(() -> {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
      });
    }
  }

  @ReactMethod
  public void enableSecureView(String imagePath) {
    Log.i("RNScreenshotPreventModule", "enable secured view called " + imagePath);

    final Activity activity = this.getReactApplicationContext().getCurrentActivity();
    if (!this.getReactApplicationContext().hasCurrentActivity() || activity == null) {
      return;
    }

    activity.runOnUiThread(() -> {
      this.createSecuredDialog(imagePath, activity);
      enabled(true);
      activity.getWindow().getDecorView().getRootView().getViewTreeObserver().addOnWindowFocusChangeListener(this);
    });
  }

  @ReactMethod
  public void disableSecureView() {
    Log.i("RNScreenshotPreventModule", "disable secured view called");

    final Activity activity = this.getReactApplicationContext().getCurrentActivity();
    if (!this.getReactApplicationContext().hasCurrentActivity() || activity == null) {
      return;
    }

    activity.runOnUiThread(() -> {
      this.securedDialog = null;
      enabled(false);
      activity.getWindow().getDecorView().getRootView().getViewTreeObserver().removeOnWindowFocusChangeListener(this);
    });
  }

  private Bitmap decodeImageUrl(String imagePath) {
    try {
      URL imageUrl = new URL(imagePath);
      return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
    } catch (IOException e) {
      if (e instanceof MalformedURLException) {
        try {
          int resourceId = this.getReactApplicationContext().getResources().getIdentifier(imagePath, "drawable", this.getReactApplicationContext().getPackageName());
          return BitmapFactory.decodeResource(this.getReactApplicationContext().getResources(), resourceId);
        } catch (Exception ee) {
          Log.e("RNScreenshotPreventModule", "exception", ee);
          return null;
        }
      }
      Log.e("RNScreenshotPreventModule", "exception", e);
      return null;
    }
  }

  private Dialog getSecuredDialog() {
    final Activity activity = this.getReactApplicationContext().getCurrentActivity();
    if (this.securedDialog == null && activity != null) {
      this.createSecuredDialog(null, activity);
    }

    return securedDialog;
  }

  private void createSecuredDialog(@Nullable String imagePath, @NonNull Activity activity) {
    this.securedDialog = new Dialog(activity, android.R.style.Theme_Light);
    this.securedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    Objects.requireNonNull(this.securedDialog.getWindow()).setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    );

    RelativeLayout layout = new RelativeLayout(activity);
    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));

    ImageView imageView = new ImageView(activity);
    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
    imageParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

    imageView.setLayoutParams(imageParams);

    // Set image resource
    if (imagePath != null) {
      Bitmap bitmap = decodeImageUrl(imagePath);

      if (bitmap != null) {
        int imageHeight = (int)(bitmap.getHeight() * ((float) activity.getResources().getDisplayMetrics().widthPixels / bitmap.getWidth()));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, activity.getResources().getDisplayMetrics().widthPixels, imageHeight, true);
        imageView.setImageBitmap(scaledBitmap);
      }

      layout.addView(imageView);
    }

    this.securedDialog.setContentView(layout);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    Log.i("RNScreenshotPreventModule", "on window focus changed " + hasFocus);

    final Activity activity = this.getReactApplicationContext().getCurrentActivity();
    if (activity == null) {
      Log.i("RNScreenshotPreventModule", "activity is null" );
      return;
    }

    boolean hasAlert = false;
    if (activity instanceof FragmentActivity) {
      List<androidx.fragment.app.Fragment> fragments = ((FragmentActivity) activity).getSupportFragmentManager().getFragments();
      for (Fragment fragment : fragments) {
        boolean isDiablog = fragment instanceof DialogFragment;
        boolean isAlert = fragment instanceof AlertFragment;
        if (isDiablog || isAlert) {
          hasAlert = true;
          break;
        }
      }
    }

    if (!hasFocus && !hasAlert){
      Log.i("RNScreenshotPreventModule", "call to hide content");
      this.getSecuredDialog().show();
      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    } else {
      Log.i("RNScreenshotPreventModule", "call to show content");
      this.getSecuredDialog().hide();
      activity.getWindow().setFlags(
              WindowManager.LayoutParams.FLAG_SECURE,
              WindowManager.LayoutParams.FLAG_SECURE
      );
    }
  }
}