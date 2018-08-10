/**
 * Created by user on 2018/8/10/010.
 */

import cheerio = require('cheerio');
import { PKG, PKG_NAME } from '../lib/util';
import { PROJECT_META_INF_INFO, PROJECT_IDEA } from '../project.config';
import * as fs from 'fs-extra';
import * as path from 'path';
import Promise = require('bluebird');

Promise.resolve(fs.readFile(PROJECT_META_INF_INFO))
	.then(function (text)
	{
		return cheerio.load(text.toString(), {
			normalizeWhitespace: false,
			xmlMode: true,
		});
	})
	.tap(function ($)
	{
		$('idea-plugin > id').text(PROJECT_IDEA + '.' + PKG_NAME);
		$('idea-plugin > name').text(PKG_NAME);
		$('idea-plugin > version').text(PKG.version);
		let vendor = $('idea-plugin > vendor');

		// @ts-ignore
		vendor.attr('url', PKG.homepage);

		vendor.text(PKG.author);
	})
	.tap(function ($)
	{
		console.log($.xml());

		return fs.outputFile(PROJECT_META_INF_INFO, $.xml())
	})
;
