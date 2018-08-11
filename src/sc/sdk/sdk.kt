package sc.sdk

import com.intellij.openapi.application.PathManager
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

class ApiResource
{
	val LOG = ApiLog(javaClass)

	fun readFile(name: String, context: Class<Any>? = null): String
	{
		val _root: String?

		if (context == null)
		{
			_root = getResourceRoot()
		}
		else
		{
			_root = getResourceRoot(context)
		}

		LOG.info("[readFile:root] ${_root}")

		/**
		 * 檢查檔案是否在 jar 包內
		 */
		val _is_jar = isJar(_root!!)

		LOG.info("[readFile:isJar] ${_is_jar}")

		if (_is_jar)
		{
			val txtReader = BufferedReader(InputStreamReader(javaClass.getResourceAsStream(name)))

			return txtReader.readLine()
		}
		else
		{
			val p = javaClass.getResource(name)

			return String(ReadFile_Files_ReadAllBytes(p))
		}
	}

	fun isJar(name: String): Boolean
	{
		return File(name).extension.equals("jar")
	}

	fun getResourceRoot(): String?
	{
		return PathManager.getResourceRoot(javaClass, "/META-INF/plugin.xml")
	}

	fun getResourceRoot(context: Class<Any>): String?
	{
		return PathManager.getResourceRoot(context, "/META-INF/plugin.xml")
	}

	fun getResourceRoot(cl: ClassLoader): String?
	{
		return PathManager.getResourceRoot(cl, "/META-INF/plugin.xml")
	}
}

/**
 * 讀取檔案
 *
 * https://funnelgarden.com/java_read_file/#FilesreadAllBytes
 */
@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(fileName: String): ByteArray
{
	val file = File(fileName)

	ApiLog.info("[ReadFile_Files_ReadAllBytes:string] ${fileName}")

	return ReadFile_Files_ReadAllBytes(file)
}

@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(uri: URI): ByteArray
{
	//val file = File(fileName.path)

	ApiLog.info("[ReadFile_Files_ReadAllBytes:uri] ${uri}")

	return ReadFile_Files_ReadAllBytes(uri.path)
}

@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(url: URL): ByteArray
{
	//val file = File(fileName.path)

	ApiLog.info("""[ReadFile_Files_ReadAllBytes:url] ${url}""")

	return ReadFile_Files_ReadAllBytes(url.path)
}

@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(file: File): ByteArray
{
	ApiLog.info("[ReadFile_Files_ReadAllBytes:file] ${file}")

	//val o = file.toPath()

	return ReadFile_Files_ReadAllBytes(file.toPath())
}

@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(path: Path): ByteArray
{
	ApiLog.info("[ReadFile_Files_ReadAllBytes:path] ${path}")

	val fileBytes = Files.readAllBytes(path)

	return fileBytes
}

@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(path: Any): ByteArray
{
	ApiLog.info("[ReadFile_Files_ReadAllBytes:any] ${path}")

	val fileBytes = Files.readAllBytes(path as Path)

	return fileBytes
}

