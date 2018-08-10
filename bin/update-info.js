"use strict";
/**
 * Created by user on 2018/8/10/010.
 */
Object.defineProperty(exports, "__esModule", { value: true });
const cheerio = require("cheerio");
const util_1 = require("../lib/util");
const project_config_1 = require("../project.config");
const fs = require("fs-extra");
const Promise = require("bluebird");
Promise.resolve(fs.readFile(project_config_1.PROJECT_META_INF_INFO))
    .then(function (text) {
    return cheerio.load(text.toString(), {
        normalizeWhitespace: false,
        xmlMode: true,
    });
})
    .tap(function ($) {
    $('idea-plugin').attr('url', util_1.PKG.homepage);
    $('idea-plugin > id').text(project_config_1.PROJECT_IDEA + '.' + util_1.PKG_NAME_ID);
    // @ts-ignore
    $('idea-plugin > name').text(util_1.PKG.ideaPlugin.title || util_1.PKG.title || util_1.PKG_NAME);
    $('idea-plugin > version').text(util_1.PKG.version);
    let vendor = $('idea-plugin > vendor');
    vendor.attr('url', util_1.PKG.homepage);
    vendor.text(util_1.PKG.author);
})
    .tap(function ($) {
    console.log($.xml());
    return fs.outputFile(project_config_1.PROJECT_META_INF_INFO, $.xml());
});
