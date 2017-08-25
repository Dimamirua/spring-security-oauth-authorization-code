const webpack = require('webpack');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const GLOBALS = {
    'process.env.NODE_ENV': JSON.stringify('production'),
    __DEV__: false
};

module.exports = function () {


    const config = {};

    config.plugins = [];

    config.entry = path.resolve(__dirname, './src/index.js');
    config.output = {
        path: __dirname + '/target/dist',
        publicPath: '/',
        filename: '[hash]bundle.js'
    };

    // config.devtool = 'source-map';

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
            }
        ]
    };

    config.plugins.push(new webpack.optimize.OccurenceOrderPlugin());
    config.plugins.push(new webpack.DefinePlugin(GLOBALS));
    // config.plugins.push(new ExtractTextPlugin('styles.css'));
    config.plugins.push(new webpack.optimize.DedupePlugin());
    config.plugins.push(new webpack.optimize.UglifyJsPlugin());


    config.resolve = {
        modules: [path.resolve(__dirname, 'src'), 'node_modules'],
        extensions: ['.js', '.jsx', '.json', '*']
    };

    return config;
}();

