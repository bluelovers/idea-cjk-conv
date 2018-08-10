/**
 * Created by user on 2018/8/10/010.
 */

import * as fs from 'fs-extra';
import * as path from 'path';
import { PKG_NAME, PKG_NAME_ID } from '../lib/util';
import { PROJECT_RESOURCES } from '../project.config';
import fg = require('fast-glob');
import Promise = require('bluebird');

const MOD = 'cjk-conv';

const RESOURCES_PATH = path.join(PROJECT_RESOURCES, 'sc', 'plugin', PKG_NAME_ID, 'data');

const options = {
	cwd: path.posix.join(path.dirname(require.resolve(MOD)), 'build/zh/convert'),
};

Promise
	.resolve(fg<string>([
		'**/*.txt',
	], options))
	.tap(function ()
	{
		console.log('start copy all table');
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

//	console.log({
//		filename,
//		file_from,
//		file_to,
//	});

	return fs.copy(file_from, file_to);
}

