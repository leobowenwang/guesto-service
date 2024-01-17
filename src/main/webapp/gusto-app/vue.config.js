const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  publicPath: process.env.NODE_ENV === 'production' ? '/guesto-service/' : '/',
  devServer: {
    port: 8081
  },
  transpileDependencies: true
});
