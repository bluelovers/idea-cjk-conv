/**
 * Created by user on 2018/8/11/011.
 */
import * as path from 'path';
import { PKG } from '../lib/util';
import { PROJECT_PRODUCTION, PROJECT_RELEASES, PROJECT_ROOT } from '../project.config';
import CrossSpawn = require('cross-spawn');

CrossSpawn(path.join(process.env.JDK_HOME, 'bin/jar'), [

	'cvf',
	path.join(PROJECT_RELEASES, PKG.name + '.jar'),

	'-C',
	path.join(PROJECT_PRODUCTION, PKG.name),

	'.',
], {
	stdio: 'inherit',
	cwd: PROJECT_ROOT,
});
