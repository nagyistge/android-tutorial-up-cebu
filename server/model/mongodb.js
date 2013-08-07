var mongoq = require('mongoq');
config = require('../config/config.js');

var db = mongoq(config.get('model:mongodb'));

/*
ALL:
- collection
READ:
- query
- columns
- sorting
- page
- offset
CREATE:
- record
UPDATE:
- query
- record
- *options
REMOVE:
- query
FINDANDMODIFY:
- query
- record
*/

module.exports = {
    
    read : function(content,cb){
      var page = content.page || 1;
      var numPerPage = content.offset || 0;
      
      db.collection(content.collection)
      .find(content.query,content.columns)
      .sort(content.sorting)
      .skip((page-1)*numPerPage)
      .limit(numPerPage)
      .toArray()
      .done(function(result){   
          cb(null,result);
      })
      .fail( function( err ) { 
          cb(err);
      });    

    },
    create : function(content,cb){
      db.collection(content.collection)
      .insert(content.record, {safe: true})
      .done(function(result){   
        cb(null,result);
      })
      .fail( function( err ) { 
        cb(err);
      });    
    },
    update : function(content,cb){
        var option = {};
        if (typeof content.option != 'undefined') { option = content.option;}
        option.safe = true;
        db.collection(content.collection)
        .update(content.query, content.record, option)
        .done(function(result){   
            cb(null,result);
        })
        .fail( function( err ) { 
            cb(err);
        });   
    },
    remove : function(content,cb){
        db.collection(content.collection)
        .remove(content.query, {safe: true})
        .done(function(result){   
            cb(null,result);
        })
        .fail( function( err ) { 
            cb(err);
        });
    },
    findAndModify : function(content, cb){
      db.collection(content.collection)
      .findAndModify(content.query, {}, content.record)
      .done(function(result){
        cb(null,result);
      })
      .fail(function(err){
        cb(err);
      });
    }, execute: function(command, cb){
      db.executeDbCommand(command)
      .done(function(result){
        cb(null,result);
      })
      .fail(function(err){
        cb(err);
      })
    }
    
};
