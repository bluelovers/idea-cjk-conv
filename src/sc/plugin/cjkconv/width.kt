package sc.plugin.cjkconv

/**
 * @todo 補完對應表
 *
 * 這個表內實在是太容易讓人頭暈
 * 而且沒有明確的分隔 非 連續 帶碼
 * 導致特殊符號部分 增加起來 超級累人
 * https://zh.wikipedia.org/wiki/%E5%85%A8%E5%BD%A2%E5%92%8C%E5%8D%8A%E5%BD%A2#%E5%8D%8A%E5%BD%A2%E5%AD%97%E7%AC%A6%E4%B8%8E%E5%85%A8%E5%BD%A2%E5%AD%97%E7%AC%A6%E7%9A%84%E6%AF%94%E8%BE%83%EF%BC%88ASCII%E5%AD%97%E7%AC%A6%EF%BC%89
 */

import sc.sdk.ApiLog

fun f2h(text: String): String
{
	val chars = text.toCharArray()
	var i = 0
	val n = chars.size
	while (i < n)
	{
		val ci = chars[i].toInt();
		var ni: Int = 0


		if (ci == 0x3000)
		{
			ni = 0x0020
		}
		else if (ci >= 0xFF01 && ci <= 0xFF5E)
		{
			ni = ci - 0xFEE0
		}
		else if (ci >= 0xFF61 && ci <= 0xFF9F)
		{
			ni = ci - 0xCF03
		}
		else if (ci >= 0x3131 && ci <= 0x314E)
		{
			ni = ci + 0xCE70
		}
		else if (ci >= 0xFF5F && ci <= 0xFF60)
		{
			ni = ci - 0xD5DA
		}

		ApiLog.debug("[f2h] ${chars[i]} ${ci} ${ni}")

		if (ni > 0)
		{
			chars[i] = ni.toChar()
		}

		i++
	}
	return String(chars)
}

fun h2f(text: String): String
{
	val chars = text.toCharArray()
	var i = 0
	val n = chars.size
	while (i < n)
	{
		val ci = chars[i].toInt()
		var ni: Int = 0

		if (ci == 0x0020)
		{
			ni = 0x3000
		}
		else if (ci >= 0x0021 && ci <= 0x007E)
		{
			ni = ci + 0xFEE0
		}
		else if (ci >= 0x3002 && ci <= 0x309C)
		{
			ni = ci + 0xCF03
		}
		else if (ci >= 0xFFA1 && ci <= 0xFFBE)
		{
			ni = ci - 0xCE70
		}
		/*
		else if (ci >= 0xFFA1 && ci <= 0xFFDC)
		{
			ni = ci - 0xCE79
		}
		*/
		else if (ci >= 0x2985 && ci <= 0x2986)
		{
			ni = ci + 0xD5DA
		}

		ApiLog.debug("[h2f] ${chars[i]} ${ci} ${ni}")

		if (ni > 0)
		{
			chars[i] = ni.toChar()
		}

		i++
	}
	return String(chars)
}
