package sc.plugin.cjkconv.action

import sc.plugin.cjkconv.f2h

open class F2HAction: AbstractAction()
{
	override fun _translate(selectedText: String): String
	{
		return f2h(selectedText)
	}
}
