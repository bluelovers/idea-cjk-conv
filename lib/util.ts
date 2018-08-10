/**
 * Created by user on 2018/8/10/010.
 */

import PKG = require('../package.json');
export { PKG }

export const PKG_NAME = PKG
	.name
	.replace(/^(?:idea|node)-/, '')
	.replace(/\-(\w)/g, function (s, c: string)
	{
		return c.toUpperCase();
	})
;
