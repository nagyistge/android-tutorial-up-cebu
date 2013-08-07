
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , http = require('http')
  , path = require('path')
  , register = require('./routes/register')
  , photo = require('./routes/photo')
  , user = require('./routes/user')
  , authorize = require('./middleware/authorize')
  , config = require('./config/config')
  , expressValidator = require('express-validator')
  , logger = require('./lib/logger')
  ;
  
var app = express();

// all environments
app.set('port', 80);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(expressValidator());
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(function(req,res,next){
  req.config = config;
  req.log = logger;
  next();
});
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/', routes.index);

app.get('/register', register.index);
app.post('/register', register.submit);
app.get('/register/success', register.success);

app.post('/user/login', user.login);

app.get('/photos/:page', authorize.require_token, photo.get);
app.post('/photo', authorize.require_token, photo.submit);

http.createServer(app).listen(config.get('app:port'), function(){
  console.log('Express server listening on port ' + config.get('app:port'));
});
