
# react-native-face-verify

## Getting started

`$ npm install react-native-face-verify --save`

### Mostly automatic installation

`$ react-native link react-native-face-verify`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-face-verify` and add `RNFaceVerify.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFaceVerify.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNFaceVerifyPackage;` to the imports at the top of the file
  - Add `new RNFaceVerifyPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-face-verify'
  	project(':react-native-face-verify').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-face-verify/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-face-verify')
  	```


## Usage
```javascript
import RNFaceVerify from 'react-native-face-verify';

// TODO: What to do with the module?
RNFaceVerify;
```
  