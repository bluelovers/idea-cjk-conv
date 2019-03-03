/**
 * Created by user on 2018/8/10/010.
 */

import * as fs from 'fs-extra';

import moment = require('moment');
import * as path from 'path';
import { initPKG, IPkg } from '../lib/util';

let data = moment();
let key = data.format('Y.M.D');

let file = path.join(__dirname, '../package.json');

fs.readJSON(file)
	.then(function (PKG: typeof import('../package.json'))
	{
		return initPKG(PKG)
	})
	.then(function (PKG)
	{
		let n: string | number = PKG.ideaPlugin.version.replace(key + '.', '');

		if (/^(\d+)$/.test(n))
		{
			n = Number(RegExp.$1) + 1
		}
		else
		{
			n = 0;
		}

		let old = PKG.ideaPlugin.version;

		PKG.ideaPlugin.version = [
			key,
			n,
		].join('.');

		PKG.version = key;

		return {
			PKG,
			old,
		}
	})
	.then(function ({
		PKG,
		old,
	})
	{
		return fs.outputJSON(file, PKG, {
				spaces: "  ",
			})
			.then(function ()
			{
				console.log(`update version from ${old} to ${PKG.ideaPlugin.version}`);
			})
		;
	})
;
