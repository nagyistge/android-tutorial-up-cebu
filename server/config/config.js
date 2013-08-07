var conf = require('nconf');
var fs = require('fs');

conf.argv()
    .env()

var node_env = conf.get('NODE_ENV') || "dev";

if (node_env == 'prod' || node_env === 'production')
    file = 'config/config.prod.json';
else if (node_env == 'dev' || node_env === 'development')
    file = 'config/config.dev.json';
else if (node_env === 'staging')
    file = 'config/config.staging.json';
else
    file = 'config/config.' + node_env + '.json';

console.log("Loading: " + file);

try {
    fs.statSync(file); 
    conf.file({ file: file });
    }
catch (err) {
		console.log("Unable to read config file. ERROR: " + err);
    process.exit();
}

module.exports = conf;

