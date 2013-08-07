var token_util = require ('../lib/token_util');

var ERRORS = {
  invalid_token: {error:{code: 6999, message:"Invalid token"}},
  no_token: {error:{code: 6998, message:"No token"}},
  expired_token: {error:{code: 6997, message:"Expired token"}}
}

exports.require_token = function (req, res, next){
  if (req.headers.authorization) {
    var token = token_util.decrypt(req.headers.authorization);
    if (token == null) {
      res.json(401, ERRORS.invalid_token);
    }
    else if (new Date(token.expiry) < new Date()) {
      res.json(401, ERRORS.expired_token);
    }
    else {
      req.token = token;
      next();
    }
  }
  else {
    res.json(401, ERRORS.no_token);
  }
}