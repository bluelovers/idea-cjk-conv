package sc.plugin.cjkconv.action

import sc.plugin.cjkconv.h2f

open class H2FAction: AbstractAction()
{
	override fun _translate(selectedText: String): String
	{
		return h2f(selectedText)
	}
}
