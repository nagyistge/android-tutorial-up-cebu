
var photo_model = require('../model/photo_model');

var ERRORS = {
  system: {error:{code: 9999, message:"System error"}},
}

exports.get = function (req, res) {
  
  photo_model.get_photos(req.params.page, function(result){
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
  
  res.json(200, {temporary: "submit"});
}