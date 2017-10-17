module.exports = function () {
    const config = {};

    config.devtool = 'inline-source-map';

    config.module = {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules|bower_components)/,
                loaders: ['babel-loader', 'eslint-loader']
            }, {
                test: /\.(css|scss)$/,
                use: ['style-loader', 'css-loader', 'sass-loader']
            }
        ]
    };

    config.resolve = {
        modules: [__dirname, 'node_modules'],
        extensions: ['.js', '.jsx', '.json', '*']
    };
    return config;
}();