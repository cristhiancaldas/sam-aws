package app.bank.user.application

import app.bank.config.LoggerDelegate
import app.bank.config.UserFilter
import app.bank.exception.Constants
import app.bank.exception.KeyException
import app.bank.exception.ValidationException
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl

class UserValidator(private val userRepository: UserRepository) {

    private val log by LoggerDelegate()

    companion object {
        val instance = UserValidator(UserRepositoryImpl.instance)
    }

    private fun validateEmail(email: String?) {
        email.let {
            if (it!!.isNotBlank() && !Constants.VALIDATE_PATTERN_EMAIL.toRegex().matches(it)) {
                log.warn("validate email : ${email}")
                throw ValidationException(KeyException.INVALID_FORMAT_EMAIL.name)
            }
        }
    }

   private fun validateUserExistWithEmail(email: String?) {
        if (!email.isNullOrEmpty()) {
            val duplicateEmail = userRepository.getUsers(UserFilter(email = email))
            if(duplicateEmail.any()){
                log.warn("Already exist an user with the same email.")
                throw ValidationException(KeyException.EMAIL_ALREADY_EXISTS.name)
            }
        }
    }

    fun validatorEmail(email: String?){
        validateEmail(email)
        validateUserExistWithEmail(email)
    }
}
