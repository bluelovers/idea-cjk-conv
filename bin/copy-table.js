"use strict";
/**
 * Created by user on 2018/8/10/010.
 */
Object.defineProperty(exports, "__esModule", { value: true });
const fs = require("fs-extra");
const path = require("path");
const util_1 = require("../lib/util");
const project_config_1 = require("../project.config");
const fg = require("fast-glob");
const Promise = require("bluebird");
const MOD = 'cjk-conv';
const RESOURCES_PATH = path.join(project_config_1.PROJECT_RESOURCES, 'sc', 'plugin', util_1.PKG_NAME_ID, 'data');
const options = {
    cwd: path.posix.join(path.dirname(require.resolve(MOD)), 'build/zh/convert'),
};
Promise
    .resolve(fg([
    '**/*.txt',
], options))
    .tap(function () {
    console.log('start copy all table');
    console.log(path.relative(__dirname, RESOURCES_PATH));
})
    .each(function (filename) {
    return copy(filename)
        .then(function () {
        console.log('[done]', filename);
    });
})
    .tap(function () {
    console.log('all done');
});
function copy(filename) {
    let file_from = require.resolve(path.join(options.cwd, filename));
    let file_to = path.join(RESOURCES_PATH, filename);
    //	console.log({
    //		filename,
    //		file_from,
    //		file_to,
    //	});
    return fs.copy(file_from, file_to);
}
