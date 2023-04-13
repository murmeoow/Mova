package dm.sample.mova.domain.usecase.account

import dm.sample.mova.domain.repositories.AccountRepository
import dm.sample.mova.domain.repositories.FileRepository
import javax.inject.Inject

class UpdateAvatarUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val fileRepository: FileRepository,
) {

    suspend operator fun invoke(path: String) {
        fileRepository.copyFileToAppDir(sourceFilePath = path, AVATAR_IMAGE_NAME)?.let {
            accountRepository.updateAccountAvatar(it)
        }
    }

    companion object {
        private const val AVATAR_IMAGE_NAME = "avatar.png"
    }

}