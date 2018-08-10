package sc.plugin.cjkconv

/**
 * 一堆雜七雜八 等待分離優化的東西
 */
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import sc.sdk.ApiLog
import sc.sdk.ReadFile_Files_ReadAllBytes

val PATH_TABLE = "/sc/plugin/cjkconv/data"

class TableLoader(val idkey: String, val pid : String, var loaded: Boolean = false)
{
	val LOG = ApiLog(javaClass)

	var from: ByteArray? = null
	var to: ByteArray? = null

	fun load(): TableLoader
	{
		if (!loaded)
		{
			val f1 = TableLoader::class.java
				.getResource("${PATH_TABLE}/${pid}/${idkey}.from.txt")
			val f2 = TableLoader::class.java
				.getResource("${PATH_TABLE}/${pid}/${idkey}.to.txt")

			from = ReadFile_Files_ReadAllBytes(f1.toURI().path)
			to = ReadFile_Files_ReadAllBytes(f2.toURI().path)

			loaded = true

			LOG.info("[load] ${idkey} ${pid}")
		}

		return this
	}

}

class MyStringTable(val string_from: String, val string_to: String)
{
	//val arr_from = string_from.toCharArray()
	//val arr_to = string_to.toCharArray()

	val arr_from = toUnicodeCharArray(string_from)
	val arr_to = toUnicodeCharArray(string_to)

	init
	{

	}

	fun copyTo(table: HashMap<Int, Int>): HashMap<Int, Int>
	{
		return build(table)
	}

	/*
	fun build(table: HashMap<Char, Char>): HashMap<Char, Char>
	{
		val table2 = table ?: HashMap<Char, Char>()
		table2.clear()

		var i = 0
		val n = Math.min(arr_from.size, arr_to.size)
		while (i < n)
		{
			val cT = Character.valueOf(arr_from[i])
			val cS = Character.valueOf(arr_to[i])

			table2.put(cT, cS)

			i++
		}

		return table2;
	}
	*/

	fun build(table: HashMap<Int, Int>): HashMap<Int, Int>
	{
		val table2 = table ?: HashMap<Int, Int>()
		table2.clear()

		val a1 = arr_from
		val a2 = arr_to

		val n = Math.min(a1.size, a2.size)
		var i = 0

		while (i < n)
		{
			val cT = a1[i]
			val cS = a2[i]

			table2.put(cT, cS)

			i++
		}

		return table2;
	}

	/*
	fun toUnicodeCharArray(s: String): IntArray
	{
		val len = s.length
		val len2 = s.codePointCount(0, s.length)
		var i = 0

		val p1 = s.codePointAt(0)
		val p2 = s.codePointAt(2)

		var c1 = Character.toChars(p1)
		var c2 = Character.toChars(p2)

		val ca = IntArray(len2)
		var ci = 0

		while (i < len)
		{
			val p = s.codePointAt(i)
			var c = Character.toChars(p)

			ca.set(ci, p)
			ci++

			i += Character.charCount(p)
		}

		return ca;
	}
	*/
}

fun toUnicodeCharArray(s: String): IntArray
{
	val len = s.length
	val len2 = s.codePointCount(0, s.length)
	var i = 0

	val p1 = s.codePointAt(0)
	val p2 = s.codePointAt(2)

	var c1 = Character.toChars(p1)
	var c2 = Character.toChars(p2)

	val ca = IntArray(len2)
	var ci = 0

	while (i < len)
	{
		val p = s.codePointAt(i)
		var c = Character.toChars(p)

		ca.set(ci, p)
		ci++

		i += Character.charCount(p)
	}

	return ca;
}

fun translate(text: String, dictionary: Map<Int, Int>): String
{
	val len = text.length

	var i = 0
	var ci = 0

	val list: MutableList<Char> = ArrayList<Char>()

	while (i < len)
	{
		val p = text.codePointAt(i)

		val found = dictionary.get(p)

		var rs: CharArray

		if (null != found)
		{
			rs = Character.toChars(found)
		}
		else
		{
			rs = Character.toChars(p)
		}

		rs.forEach { c: Char ->

			list.add(c)

		}

		//list.addAll(rs.toCollection())

		//list.add(rs)

		//list.addAll(ci, rs)

		ci++

		i += Character.charCount(p)
	}

	return list.joinToString(separator = "")
}

fun replaceSelectionText(editor: Editor, model: SelectionModel, text: String)
{
	val start = model.getSelectionStart()
	val end = model.getSelectionEnd()

	editor
		.document
		.replaceString(start, end, text)
	;
}
