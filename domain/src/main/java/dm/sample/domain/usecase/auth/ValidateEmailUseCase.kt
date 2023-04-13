package dm.sample.mova.domain.usecase.auth

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        return (email.length in 3..32)
    }
}