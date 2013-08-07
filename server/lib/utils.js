var crypto = require('crypto');
var config = require('../config/config');

module.exports = {
  hashPassword: function (password) {
    return crypto.createHash('sha256').update(password + config.get('app:passwor_salt')).digest('hex');
  },
  get_url: function(req) {
    return req.protocol + "://" + req.get('host') + req.url;
  },
  merge : function() {
    var obj = {},
    i = 0,
    il = arguments.length,
    key;
    if (il === 0) {
      return obj;
    }
    for (; i < il; i++) {
      for (key in arguments[i]) {
        if (arguments[i].hasOwnProperty(key)) {
          obj[key] = arguments[i][key];
        }
      }
    }
    return obj;
	},
	safeMerge : function(src,dst){
		for(key in src){
			if(dst.hasOwnProperty(key)){
				src[key] = dst[key];
			}
		}
		return src;
	},
	applyFilter : function(jsonobj, jsonfilter) {
	  var newobj = {};
	  for(key in jsonfilter){
			if(jsonobj.hasOwnProperty(key)){
				newobj[key] = jsonobj[key];
			}
		}
		return newobj;
	},
	parse : function(obj){
		try{
			return JSON.parse(obj);
		}
		catch(e){
			return false
	  }
	},
	computePast: function(date) {
	  var now = new Date();
	  var oneDay = 24*60*60*1000;
    var oneHour = 60*60*1000;
    var oneMinute = 60*1000;
    var difference = (now.getTime() - date.getTime());
    
    if (difference < oneHour) {
      var val = Math.round(difference/oneMinute);
      return val + (val==1? ' minute ago' : ' minutes ago');
    }
    else if (difference < oneDay) {
      var val = Math.round(difference/oneHour);
      return val + (val==1? ' hour ago' : ' hours ago');
    }
    else {
      var val = Math.round(difference/oneDay);
      return val + (val==1? ' day ago' : ' days ago');
    }
	},
	resize: function(url, w, h) {
	  if(config.get('images:resize') == true) {
	    if (h == 0){
  	    return 'http://hr-pulsesubscriber.appspot.com/image?link=' + encodeURIComponent(url) + '&width=' + w;
	    }
	    else {
  	    return 'http://hr-pulsesubscriber.appspot.com/image?link=' + encodeURIComponent(url) + '&height='+h+'&width=' + w;
	    }
	  }
	  else {
	    return url;
	  }
	}
}