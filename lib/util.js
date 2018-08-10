"use strict";
/**
 * Created by user on 2018/8/10/010.
 */
Object.defineProperty(exports, "__esModule", { value: true });
const PKG = require("../package.json");
exports.PKG = PKG;
exports.PKG_NAME = PKG
    .name
    .replace(/^(?:idea|node)-/, '')
    .replace(/\-(\w)/g, function (s, c) {
    return c.toUpperCase();
});
