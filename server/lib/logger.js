/*
  Reference: http://docs.nodejitsu.com/articles/intermediate/how-to-log
*/

var config = require('../config/config.js');

var debugLevel = config.get('logs:level');
var showTimeStamp = config.get('logs:timestamp');
var delimiter = config.get('logs:delimiter');

console.log("Starting logs: debugLevel=" + debugLevel);

function log(level, tag, message) {
  var levels = ['error', 'warn', 'info', 'debug'];
  if (levels.indexOf(level) <= levels.indexOf(debugLevel) ) {
    if (typeof message !== 'string') {
      message = JSON.stringify(message);
    };
    var d = new Date();
    if(showTimeStamp){
      console.log(tag+delimiter+level+delimiter+d+': '+message);
    }
    else {
      console.log(tag+delimiter+level+': '+message);
    }
  }
}

exports.log = log;

exports.d = function(tag, message) {
  log('debug', tag, message);
}

exports.i = function(tag, message) {
  log('info', tag, message);
}

exports.w = function(tag, message) {
  log('warn', tag, message);
}

exports.e = function(tag, message) {
  log('error', tag, message);
}

/* Usage:
  var logger = require('./logger');
  logger.log('info', TAG, message);
  logger.d(TAG, message)
*/