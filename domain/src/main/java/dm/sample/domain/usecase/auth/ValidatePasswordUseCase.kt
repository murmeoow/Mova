package dm.sample.mova.domain.usecase.auth

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        return (password.length in 8..32)
    }
}