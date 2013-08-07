

exports.add_photo = function(user_id, photo, next) {
  
}

exports.get_photos = function (page, next) {
  
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

