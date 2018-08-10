/**
 * Created by user on 2018/8/10/010.
 */
export interface IPkgIdeaPlugin {
    title?: string;
}
export declare type IPkg = typeof import('../package.json') & {
    ideaPlugin?: IPkgIdeaPlugin;
};
export declare const PKG: IPkg;
export declare const PKG_NAME: string;
export declare const PKG_NAME_ID: string;
export declare function initPKG<T>(PKG: T): T & IPkg;
