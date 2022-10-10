
# react-native-screenshot-prevent

## Getting started

`$ npm install react-native-screenshot-prevent --save`

### Mostly automatic installation

### React-Native version 0.69.X and higher: on IOS you might use only `pod install` in your ios folder

`$ react-native link react-native-screenshot-prevent`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-screenshot-prevent` and add `RNScreenshotPrevent.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNScreenshotPrevent.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.killserver.RNPreventScreenshotPackage;` to the imports at the top of the file
  - Add `new RNPreventScreenshotPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-screenshot-prevent'
  	project(':react-native-screenshot-prevent').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-screenshot-prevent/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-screenshot-prevent')
  	```



## Usage
```javascript

import RNPreventScreenshot from 'react-native-screenshot-prevent';

/* (IOS, Android) for android might be the only step to get secureView, on IOS enables blurry view when app goes into inactive state */
RNPreventScreenshot.enabled(true/false);

/* (IOS) enableSecureView for IOS13+ - creates hidden secureTextField which prevents Application UI capture on screenshots */
RNPreventScreenshot.enableSecureView();

/* (IOS) notification handler - notifies when user has taken screenshot (yes, after taking) - you can show alert or do some actions */
/** @returns object with .unsubscribe() method */
RNPreventScreenshot.addListener(fn);
```

  
