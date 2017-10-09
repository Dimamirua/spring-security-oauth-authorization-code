const webpack = require('webpack');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');


module.exports = function () {
    const config = {};

    config.plugins = [];

    config.entry = path.resolve(__dirname, './src/index.js');
    config.output = {
        filename: 'bundle.js'
    };

    config.devtool = 'source-map';

    config.plugins.push(new HtmlWebpackPlugin({
        inject: true,
        template: __dirname + '/src/template/index.html',
    }));

    config.plugins.push(new webpack.HotModuleReplacementPlugin());

    config.module = {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules|bower_components)/,
                loaders: ['babel-loader', 'eslint-loader']
            }, {
                test: /\.(css|scss)$/,
                use: ['style-loader', 'css-loader','sass-loader']
            }
        ]
    };

    config.devServer = {
        host: 'localhost',
        port: '7070',
        hot: true,
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
    config.resolve = {
        modules: [__dirname, 'node_modules'],
        extensions: ['.js', '.jsx', '.json', '*']
    };
    return config;
}();

