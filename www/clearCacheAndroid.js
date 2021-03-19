var exec = require('cordova/exec');

exports.clearCache = function(success, error) {
    exec(success, error, 'clearCacheAndroid', 'clearCache');
};