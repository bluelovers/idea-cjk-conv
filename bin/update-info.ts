/**
 * Created by user on 2018/8/10/010.
 */

import cheerio = require('cheerio');
import { PKG, PKG_NAME, PKG_NAME_ID } from '../lib/util';
import { PROJECT_META_INF_INFO, PROJECT_IDEA, PROJECT_ROOT, PROJECT_PRODUCTION } from '../project.config';
import * as fs from 'fs-extra';
import * as path from 'path';
import Promise = require('bluebird');
import Marked = require('marked');

Promise.resolve(fs.readFile(PROJECT_META_INF_INFO))
	.then(function (text)
	{
		// @ts-ignore
		return cheerio.load(text.toString(), {
			normalizeWhitespace: false,
			xmlMode: true,
			xml: true,
		});
	})
	.tap(async function ($)
	{
		$('idea-plugin').attr('url', PKG.homepage);

		$('idea-plugin > id').text(PROJECT_IDEA + '.' + PKG_NAME_ID);
		// @ts-ignore
		$('idea-plugin > name').text(PKG.ideaPlugin.title || PKG.title || PKG_NAME);
		$('idea-plugin > version').text(PKG.version);
		let vendor = $('idea-plugin > vendor');

		vendor.attr('url', PKG.homepage);

		vendor.text(PKG.author);

		{
			let readme = await fs.readFile(path.join(PROJECT_ROOT, 'README.md'));

			let md = Marked(readme.toString(), {
				breaks: true,
				headerIds: false,
				gfm: true,
			});

			let elem = $('idea-plugin > description');

			updateCDATA(elem, md);
			//elem[0].children[0].children[0].data = md;
		}
	})
	.tap(function ($)
	{
		//console.log($.xml());

		let xml = $.xml();

		return Promise.all([
			fs.outputFile(PROJECT_META_INF_INFO, xml),
			fs.outputFile(path.join(PROJECT_PRODUCTION, `${PKG.name}/META-INF/plugin.xml`), xml),
		])
	})
;

function updateCDATA(elem: Cheerio, value: string)
{
	return elem[0].children[0].children[0].data = value;
}

