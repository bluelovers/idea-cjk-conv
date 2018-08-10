package sc.sdk

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil

private val DEFAULT_NAME = "#sc.plugin.sdk"

fun ApiLog() = Logger.getInstance(DEFAULT_NAME)

fun ApiLog(name: Any?): Logger
{
	if (name is Class<*>)
	{
		return Logger.getInstance(name)
	}
	else if (name != null && !StringUtil.isEmptyOrSpaces(name.toString()))
	{
		return Logger.getInstance(name.toString())
	}

	return Logger.getInstance(DEFAULT_NAME)
}

val ApiLog = ApiLog()
