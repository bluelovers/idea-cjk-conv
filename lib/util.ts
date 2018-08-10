/**
 * Created by user on 2018/8/10/010.
 */

export interface IPkgIdeaPlugin
{
	title?: string,
}

export type IPkg = typeof import('../package.json') & {
	ideaPlugin?: IPkgIdeaPlugin,
}

export const PKG: IPkg = require('../package.json');

initPKG(PKG);

export const PKG_NAME: string = PKG
	.name
	.replace(/^(?:idea|node)-/, '')
	.replace(/\-(\w)/g, function (s, c: string)
	{
		return c.toUpperCase();
	})
	.replace(/^(\w)/g, function (s, c: string)
	{
		return c.toUpperCase();
	})
;

export const PKG_NAME_ID = PKG_NAME.toLowerCase();

export function initPKG<T>(PKG: T): T & IPkg
{
	// @ts-ignore
	PKG.ideaPlugin = PKG.ideaPlugin || {};

	// @ts-ignore
	return PKG
}
