package sc.sdk

import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * 讀取檔案
 *
 * https://funnelgarden.com/java_read_file/#FilesreadAllBytes
 */
@Throws(IOException::class)
fun ReadFile_Files_ReadAllBytes(fileName: String): ByteArray
{
	val file = File(fileName)

	ApiLog.info("[ReadFile_Files_ReadAllBytes] ${fileName} ${file}")

	val fileBytes = Files.readAllBytes(file.toPath())

	return fileBytes
}
