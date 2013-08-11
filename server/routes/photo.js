
var photo_model = require('../model/photo_model');
var async = require('async');
var crypto = require('crypto');
var fs = require('fs');

var ERRORS = {
  system: {error:{code: 9999, message:"System error"}},
  validation: {error:{code: 8001, message:"Incorrect request"}},
}

var FIELD_ERRORS = {
  photo_data: "Photo data missing",
  caption: "Caption missing"
}


var TAG = 'routes/photo';

exports.get = function (req, res) {
  
  photo_model.get_photos(req.params.page, 10, function(result){
    if(result.error_code) {
      res.json(200, ERRORS.system);
    }
    else {
      res.json(200, result);
    }
  });
  //res.json(200, {temporary: "get"});
}

exports.submit = function (req, res) {
  
  async.auto({
    validation: function(callback){
      req.assert('photo_data', FIELD_ERRORS.photo_data).notEmpty();
      //req.assert('caption', FIELD_ERRORS.caption).notEmpty();
      var val_errors = req.validationErrors();  
    	if(val_errors){
        res.json(200, ERRORS.validation);
    	}
      else {
        callback(null);
      }
    },
    write_image_to_file: ['validation', function(callback){
      req.log.d(TAG, "write_image_to_file");
      // get the base 64 image
      var image_base64 = req.body.photo_data;
      var d = new Date();
      var md5sum = crypto.createHash('md5');
      var filename = md5sum.update(d.getTime() + '-' + req.token.user_id).digest("hex") + '.jpg';
      
      fs.writeFile(config.get('app:images_folder') + '/' +  filename, image_base64, 'base64', function(err){
        if (err){
          req.log.e(TAG, err)
          res.json(200, ERRORS.system);
        }
        else {
          callback(null, filename)
        }
      }); // WriteFile
    }],
    save_photo_url: ['write_image_to_file', function(callback, cbres){
      var photo_url = req.config.get('app:images_url') + '/' + cbres.write_image_to_file;
      var photo =  {};
      photo.photo_url = photo_url;
      photo.caption = req.body.caption;
      photo.user_name = req.token.name;
      photo_model.add_photo(req.token.user_id, photo, function(result){
        if (result.error_code){
          res.json(200, ERRORS.system);
        }
        else {
          res.json(200, result[0]);
        }
      });
      
    }]
  }, function (error, result) {
    // do nothing
  }); 
  
}
