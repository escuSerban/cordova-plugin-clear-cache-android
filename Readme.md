# Clear Cache Android
Cordova Plugin for Android Platform to perform the basic functionality of emptying the cached data on App startup.

## Installation
cordova plugin add https://github.com/escuSerban/cordova-plugin-clear-cache-android

## Usage
 function success(res) { console.log(res); }

setTimeout(function() {
     cordova.exec(success, null, "clearCacheAndroid", "clearCache");
    console.log("cache empty");
}, 1000);
