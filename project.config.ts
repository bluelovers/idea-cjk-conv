/**
 * Created by user on 2017/8/13/013.
 */

import * as path from 'path';

export const PROJECT_IDEA = 'sc.plugin';

export const PROJECT_ROOT = path.join(__dirname);

export const PROJECT_RESOURCES = path.join(PROJECT_ROOT, 'resources');

export const PROJECT_META_INF_INFO = path.join(PROJECT_RESOURCES, 'META-INF/plugin.xml');

import * as self from './project.config';
export default self;
