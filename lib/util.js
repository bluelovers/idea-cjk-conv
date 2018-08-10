"use strict";
/**
 * Created by user on 2018/8/10/010.
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.PKG = require('../package.json');
initPKG(exports.PKG);
exports.PKG_NAME = exports.PKG
    .name
    .replace(/^(?:idea|node)-/, '')
    .replace(/\-(\w)/g, function (s, c) {
    return c.toUpperCase();
})
    .replace(/^(\w)/g, function (s, c) {
    return c.toUpperCase();
});
exports.PKG_NAME_ID = exports.PKG_NAME.toLowerCase();
function initPKG(PKG) {
    // @ts-ignore
    PKG.ideaPlugin = PKG.ideaPlugin || {};
    // @ts-ignore
    return PKG;
}
exports.initPKG = initPKG;
