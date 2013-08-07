var db = require('./mongodb');
var logger = require('../lib/logger');

var COLUMNS = {
  login: {
    email: true,
    name: true,
    _id: true,
    user_id: true
  }
}

var ERRORS = {
  model_error: {error_code: "999", message: "model error"}
}

var TAG = 'model/account_model';

module.exports = {
  login: function(email, password, next) {
    // Get the user with the username and password
    var content = {};
    content.collection = 'users';
    content.query = {email: email, password: password};
    content.columns = COLUMNS.login;
    db.read(content, function(error, result){
      if (error){
        logger.e(TAG, "DB Error on register");
        logger.e(TAG, error);
        next(ERRORS.model_error);        
      }
      else {
        logger.d(TAG, result);
        next(result);
      }
    })
  },
  register: function(user, next) {
    var content = {};
    content.collection = 'users';
    getNextSequence(function(error, result){
      if (error) {
        next(ERRORS.model_error);        
      }
      else {
        user.user_id = "" + result.seq;
        content.record = user;
        db.create(content, function(error, result){
          if (error){
            logger.e(TAG, "DB Error on register");
            logger.e(TAG, error);
            next(ERRORS.model_error);        
          }
          else {
            next(result);
          }
        });
      }
    });
  },
  check_duplicate: function(email, next) {
    var content = {};
    content.collection = 'users';
    content.query = {email: email};
    content.columns = COLUMNS.login;
    db.read(content, function(error, result){
      if (error) {
        logger.e(TAG, "DB Error on check_duplicate");
        logger.e(TAG, error);
        next(ERRORS.model_error);
      }
      else {
        logger.d(TAG, result);
        if (result.length!=0) {
          next(true);
        }
        else {
          next(false);
        }
      }
    })
  },
  get_user: function(user_id, next) {
    var content = {};
    content.collection = 'users';
    content.query = {user_id: user_id};
    content.columns = COLUMNS.login;
    db.read(content, function(error, result){
      if (error){
        logger.e(TAG, "DB Error on get_user");
        logger.e(TAG, error);
        next(ERRORS.model_error);        
      }
      else {
        logger.d(TAG, result);
        next(result);
      }
    })
  }
}

function getNextSequence(next) {
  var content = {};
  content.collection = 'counters';
  content.query = {_id: 'accounts'};
  content.record = { $inc: { seq: 1 } };
  db.findAndModify(content, function(error, result){
    next(error, result);
  })
}

// db.counters.insert({_id: "accounts", seq: 0})
