
# react-native-screenshot-prevent

## Getting started

`$ npm install react-native-screenshot-prevent --save`

## fork: https://github.com/suehok/react-native-screenshot-prevent

### Mostly automatic installation

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

RNPreventScreenshot.enabled(true/false);
```
  