
var crypto = require('crypto');
var config = require('../config/config');
var logger = require('../lib/logger');

var TAG = 'lib/token_util';

exports.encrypt = function (param) {
  var json_str = JSON.stringify(param);
  logger.d(TAG, 'encrypt: ' + json_str);
  var cipher = crypto.createCipher('aes-256-cbc', config.get('app:cipher'));
  var encrypted_str = cipher.update(json_str, 'utf8', 'base64') + cipher.final('base64');
  logger.d(TAG, encrypted_str);
  return encrypted_str;
  // TODO: perform encryption
}

exports.decrypt = function (param) {
  try {
    // TODO: decrypt the param
    logger.d(TAG, 'decrypt: ' + param);
    var decipher = crypto.createDecipher('aes-256-cbc', config.get('app:cipher'));
    var decrypted_str = decipher.update(param, 'base64', 'utf8') + decipher.final('utf8');
    logger.d(TAG, decrypted_str);
    var json = JSON.parse(decrypted_str);
    return json;
  }
  catch (e) {
    return false;
  }
}

