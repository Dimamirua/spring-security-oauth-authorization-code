const webpackConfig = require('./webpack.config.tests');

module.exports = function (config) {
    const configuration = {};

    configuration.basePath = './';
    configuration.frameworks = ['mocha'];
    configuration.browsers = ['PhantomJS'];
    configuration.webpack = webpackConfig;
    configuration.files = ['tests/index.js'];

    configuration.preprocessors = {'tests/index.js': ['webpack']};


    return config.set(configuration);

};