package app.bank.user.application

import app.bank.config.LoggerDelegate
import app.bank.exception.Constants
import app.bank.exception.KeyException
import app.bank.exception.ValidationException
import app.bank.shared.UserDto

class UserValidator {

    private val log by LoggerDelegate()
    companion object {
        val instance = UserValidator()
    }
    fun validatorEmail(userDto: UserDto) {
        userDto.email.let {
            if (it.isNotBlank() && Constants.VALIDATE_PATTERN_EMAIL.toRegex().matches(it)) {
                log.info("validate email : ${userDto.email}")
                throw ValidationException(KeyException.INVALID_FORMAT_EMAIL.name)
            }
        }
    }
}
