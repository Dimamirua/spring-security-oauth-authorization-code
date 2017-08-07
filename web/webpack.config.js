const path = require('path');

console.log("app");

module.exports = function () {
    const config = {};

    config.entry = path.resolve(__dirname, './src/index.js');
    config.output={
        filename:'bundle.js'
    };

    return config;
}();