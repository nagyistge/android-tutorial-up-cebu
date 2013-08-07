var async = require('async');
var utils = require('../lib/utils');
var token_util = require('../lib/token_util');
var account_model = require('../model/account_model');

var ERRORS = {
  system: {error:{code: 9999, message:"System error"}},
  validation: {error:{code: 8001, message:"Incorrect request"}},
  login: {error:{code: 7001, message:"Incorrect email/password"}},
};
var FIELD_ERRORS = {
  email: "Invalid email address",
  password: "Invalid password (must be 8-100 chars.)",
  name: "Invalid name (must be 4-200 chars.)"
}

var TAG = 'routes/user';

exports.login = function (req, res) {
  async.auto({
    validation: function(cb, cbres) {
      req.assert('email', FIELD_ERRORS.email).isEmail();
      req.assert('password', FIELD_ERRORS.password).len(8,100);
      var val_errors = req.validationErrors(true);
    	if(val_errors) {
    	  res.json(200, ERRORS.validation);
    	}
    	else cb(null, true);
    },
    login: ['validation', function(cb, cbres){
      account_model.login(req.body.email, utils.hashPassword(req.body.password), function(result){
        if(result.error_code) {
          res.json(200, ERRORS.system);
        }
        else {
          req.log.d(TAG, 'account_model.login');
          req.log.d(TAG, result);
          if(result.length == 0){
        	  res.json(200, ERRORS.login);            
          }
          else {
            var user = result[0];
            var raw_token = {};
            raw_token.user_id = user.user_id;
            var today = new Date();
            raw_token.expiry = new Date(today.getTime() + (24 * 60 * 60 * 1000)); // +1 day
            var token_val = token_util.encrypt(raw_token);
            var token = {};
            token.token = token_val;
            token.token_expiry = 24*60*60*1000;
            var profile = {email: user.email, name: user.name};
            token.profile = profile;
            res.json(200, token);
          }
        }
      })
    }]
  },
  function (error, result){
    // do nothing
  });
  
}

