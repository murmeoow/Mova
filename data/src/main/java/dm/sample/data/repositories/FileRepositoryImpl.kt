package dm.sample.data.repositories

import android.content.Context
import android.net.Uri
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.repositories.FileRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


class FileRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    gson: Gson,
) : FileRepository, BaseRepository(gson) {

    override suspend fun copyFileToAppDir(sourceFilePath: String, destinationFileName: String): String? = withContext(Dispatchers.IO) {
        val uri = Uri.parse(sourceFilePath)

        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.let {
            val destinationFile = File(context.filesDir, destinationFileName)
            copyInputStreamToFile(inputStream, destinationFile)
            inputStream.close()
            return@withContext destinationFile.path
        }
        return@withContext null
    }


    private fun copyInputStreamToFile(inputStream: InputStream, file: File): Boolean {
        return try {
            FileOutputStream(file, false).use { outputStream ->
                var read: Int
                val bytes = ByteArray(DEFAULT_BUFFER_SIZE)
                while (inputStream.read(bytes).also { read = it } != -1) {
                    outputStream.write(bytes, 0, read)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

}