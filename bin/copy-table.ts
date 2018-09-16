/**
 * Created by user on 2018/8/10/010.
 */

import * as fs from 'fs-extra';
import * as path from 'path';
import { PKG, PKG_NAME, PKG_NAME_ID } from '../lib/util';
import { PROJECT_PRODUCTION, PROJECT_RESOURCES } from '../project.config';
import fg = require('fast-glob');
import Promise = require('bluebird');
import CjkConv = require('cjk-conv');

const MOD = 'cjk-conv';

const RESOURCES_PATH = path.join(PROJECT_RESOURCES, 'sc', 'plugin', PKG_NAME_ID, 'data');
const PRODUCTION_PATH = path.join(PROJECT_PRODUCTION, PKG.name, 'sc', 'plugin', PKG_NAME_ID, 'data');

const options = {
	cwd: path.posix.join(path.dirname(require.resolve(MOD)), 'build/zh/convert'),
};

Promise
	.resolve(fg<string>([
		'**/*.txt',
	], options))
	.tap(function ()
	{
		console.log('start copy all table', CjkConv.version);
		console.log(path.relative(__dirname, RESOURCES_PATH));
	})
	.each(function (filename: string)
	{
		return copy(filename)
			.then(function ()
			{
				console.log('[done]', filename);
			})
		;
	})
	.tap(function ()
	{
		console.log('all done');
	})
;

function copy(filename: string)
{
	let file_from = require.resolve(path.join(options.cwd, filename));
	let file_to = path.join(RESOURCES_PATH, filename);
	let file_to2 = path.join(PRODUCTION_PATH, filename);

	console.dir({
		filename,
		//file_from,
		file_to: path.relative(__dirname, file_to),
		file_to2: path.relative(__dirname, file_to2),
	}, {
		colors: true,
	});

	return Promise.all([
		fs.copy(file_from, file_to),
		fs.copy(file_from, file_to2),
	]);
}

