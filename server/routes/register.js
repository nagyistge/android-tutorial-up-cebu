var async = require('async');
var account_model = require('../model/account_model');
var utils = require('../lib/utils')

var REGISTER_ERRORS = {
  duplicate_email: {code: "1", message: "Email address already registered"}
}

var FIELD_ERRORS = {
  email: "Invalid email address",
  password: "Invalid password (must be 8-100 chars.)",
  name: "Invalid name (must be 4-200 chars.)"
}

var VALIDATION_ERROR = {code: -1, message:"Please fix the following errors:"};

var TAG = 'routes/register';

exports.index = function (req, res) {
  var content = {};    
  content.req = req;
  res.render('register', content);
}

exports.submit = function(req, res) {
  async.auto({
    validation: function(cb, cbres) {
      req.assert('reg_email', FIELD_ERRORS.email).isEmail();
      req.assert('reg_password', FIELD_ERRORS.password).len(8,100);
      req.assert('reg_name', FIELD_ERRORS.name).len(4,200);
      var error = VALIDATION_ERROR;
      var val_errors = req.validationErrors(true);
      error.fields = val_errors;
    	if(val_errors) cb(error, true);
    	else cb(null, true);
    },
    checkemail: ['validation', function(cb, cbres) {
      account_model.check_duplicate(req.body.reg_email, function(result){
        if (result.error_code) {
          cb(result, null);
        }
        else {
          if (result == true) {
            cb(REGISTER_ERRORS.duplicate_email, null);
          }
          else {
            cb(null, true);
          }
        }
      });
    }],
    register: ['checkemail', function(cb, cbres) {
      var user = {};
      user.email = req.body.reg_email;
      user.password = utils.hashPassword(req.body.reg_password);
      user.name = req.body.reg_name;
      
      account_model.register(user, function(result){
        if (result.error_code) {
          cb(result, null);
        }
        else {
          var user = result[0];
          req.log.d(TAG, "success registering");
          req.log.d(TAG, result);
          res.redirect('/register/success')
        }
      });
    }]
  },
  function(error, result) {
    if (error) {
      var content = {};
      if (error.error_code == 999){
        content.error_message = "Unknown Error. Please try again.";
      }
      else if (error == REGISTER_ERRORS.duplicate_email) {
        content.error_message = REGISTER_ERRORS.duplicate_email.message;
      }
      else if (error.code == VALIDATION_ERROR.code){
        content.error_message = VALIDATION_ERROR.message;
        content.error_fields = error.fields;
      }
      content.req = req;
      res.render('register', content);
    }
  });
  
}

exports.success = function(req, res) {
  var content = {};    
  content.req = req;
  res.render('register_success', content);
}