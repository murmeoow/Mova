package dm.sample.mova.domain.repositories

interface FileRepository {

    suspend fun copyFileToAppDir(sourceFilePath: String, destinationFileName: String): String?

}