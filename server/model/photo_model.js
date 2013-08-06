var db = require('./mongodb');
var logger = require('../lib/logger');

var COLUMNS = {
  photo: {
    photo_id: true,
    user_id: true,
    photo_url: true,
    user_name: true,
    date_uploaded: true,
    caption: true
  }
}

var ERRORS = {
  model_error: {error_code: "999", message: "model error"}
}

var TAG = 'model/account_model';

exports.add_photo = function(user_id, photo, next) {
  var content = {};
  content.collection = 'photos';
  getNextSequence(function(error, result){
    if (error) {
      next(ERRORS.model_error);        
    }
    else {
      photo.photo_id = "" + result.seq;
      photo.user_id = user_id;
      //photo.date_created = new Date();
      content.record = photo;
      db.create(content, function(error, result){
        if (error){
          logger.e(TAG, "DB Error on add_photo");
          logger.e(TAG, error);
          next(ERRORS.model_error);        
        }
        else {
          next(result);
        }
      });
    }
  });
}

exports.get_photos = function(page, offset, next){
  var content = {};
  content.collection = 'photos';
  content.columns = COLUMNS.photo;
  content.sorting = {_id: -1};
  content.page = page;
  content.offset = offset;
  logger.d(TAG, content);
  db.read(content, function(error, result){
    logger.d(TAG, error);
    logger.d(TAG, result);
    if (error){
      logger.e(TAG, "DB Error on get_journeys");
      logger.e(TAG, error);
      next(ERRORS.model_error);        
    }
    else {
     next(result);
    }
  });
}


function getNextSequence(next) {
  var content = {};
  content.collection = 'counters';
  content.query = {_id: 'photos'};
  content.record = { $inc: { seq: 1 } };
  db.findAndModify(content, function(error, result){
    next(error, result);
  })
}

