[![SWUbanner](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/banner2-direct.svg)](https://github.com/vshymanskyy/StandWithUkraine/blob/main/docs/README.md)

# react-native-screenshot-prevent

### This fork contains fully working blank screenshot on IOS13+ including screen recording
### App layout is white / or black in dark theme

#### For now you might disable RNPreventScreenshot.enableSecureView() in development mode (check __DEV__ variable)
#### because disableSecureView() is not working yet correctly


## Getting started

`$ npm install react-native-screenshot-prevent --save`

### Mostly automatic installation

### React-Native version 0.59.X and higher: on IOS you might use only `pod install` in your ios folder

`$ react-native link react-native-screenshot-prevent`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-screenshot-prevent` and add `RNScreenshotPrevent.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNScreenshotPrevent.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.killserver.screenshotprev.RNScreenshotPreventPackage;` to the imports at the top of the file
  - Add `new RNScreenshotPreventPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-screenshot-prevent'
  	project(':react-native-screenshot-prevent').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-screenshot-prevent/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
       implementation project(':react-native-screenshot-prevent')
  	```



## Usage
```javascript

// sample code

import RNScreenshotPrevent, { addListener } from 'react-native-screenshot-prevent';

/* (IOS, Android) for android might be the only step to get secureView
 * on IOS enables blurry view when app goes into inactive state
 */
RNScreenshotPrevent.enabled(true/false);

/* (IOS) enableSecureView for IOS13+ 
 * creates a hidden secureTextField which prevents Application UI capture on screenshots
 */
if(!__DEV__) RNScreenshotPrevent.enableSecureView();

/* (IOS) disableSecureView for IOS13+ 
 * remove a hidden secureTextField which prevents Application UI capture on screenshots
 */
if(!__DEV__) RNScreenshotPrevent.disableSecureView();

/* (IOS) notification handler
 * notifies when user has taken screenshot (yes, after taking) - you can show alert or do some actions
 *
 * @param {function} callback fn
 * @returns object with .remove() method
 */
addListener(fn);

/** example using the listener */
useEffect(() => {
	const subscription = RNScreenshotPrevent.addListener(() => {
		console.log('Screenshot taken');
		showAlert({
			title: 'Warning',
			message: 'You have taken a screenshot of the app. This is prohibited due to security reasons.',
			confirmText: 'I understand'
		});
	})

	return () => {
		subscription.remove();
	}
}, []);

```

  
[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/killeserver)
