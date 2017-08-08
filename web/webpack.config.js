const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const nodeEnvironment = process.env.NODE_ENV;
const isDev = nodeEnvironment === 'development';
const isProd = nodeEnvironment === 'production';

console.log(isDev);


module.exports = function () {

    const config = {};

    config.plugins = [];

    config.entry = path.resolve(__dirname, './src/index.js');
    config.output = {
        filename: 'bundle.js'
    };

    config.devtool = isDev ? 'source-map' : false;

    config.plugins.push(new HtmlWebpackPlugin({
        inject: true,
        // favicon: 'src/images/favicon/favicon.ico',
        template: __dirname + '/src/html/index.html',
    }));

    if (isDev) {
        config.devServer = {
            host: 'localhost',
            port: '7070',
            proxy: {
                '/api/**': {
                    target: 'http://localhost:8080/',
                    secure: false
                }
            },
            watchOptions: {
                aggregateTimeout: 100,
                ignored: /node_modules/,
                poll: 500
            }
        };
    }
    return config;
}();

