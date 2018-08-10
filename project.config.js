"use strict";
/**
 * Created by user on 2017/8/13/013.
 */
Object.defineProperty(exports, "__esModule", { value: true });
const path = require("path");
exports.PROJECT_IDEA = 'sc.plugin';
exports.PROJECT_ROOT = path.join(__dirname);
exports.PROJECT_RESOURCES = path.join(exports.PROJECT_ROOT, 'resources');
exports.PROJECT_META_INF_INFO = path.join(exports.PROJECT_RESOURCES, 'META-INF/plugin.xml');
const self = require("./project.config");
exports.default = self;
