package sc.plugin.cjkconv.action

import com.intellij.openapi.project.DumbAware
import sc.plugin.cjkconv.MyStringTable
import sc.plugin.cjkconv.TableLoader
import sc.plugin.cjkconv.translate
import javax.swing.Icon

abstract class AbstractConvAction(val idkey: String, val pid: String, icon: Icon? = null) : AbstractAction(icon), DumbAware
{
	var _inited = false
	val _hashmap = HashMap<Int, Int>()

	/**
	 * lazy load
	 * 想說等到實際第一次使用才來載入轉換表
	 * 實際上對於記憶體有沒有幫助不曉得
	 */
	fun _load()
	{
		if (!_inited)
		{
			val table = TableLoader(idkey, pid).load()

			val _from: String
			val _to: String

			if (table.from is String)
			{
				_from = table.from!!
			}
			else
			{
				_from = String(table.from as ByteArray)
			}

			if (table.to is String)
			{
				_to = table.to!!
			}
			else
			{
				_to = String(table.to as ByteArray)
			}

			MyStringTable(
				string_from = _from,
				string_to = _to
			).copyTo(_hashmap)

			_inited = true
		}
	}

	override fun _translate(selectedText: String): String
	{
		this._load()

		return translate(selectedText, _hashmap)
	}

}
