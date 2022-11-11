package app.bank.user.application

import app.bank.bean.User
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl

class UserCreator(
    private val userOpsRepository: UserRepository,
    private val userValidator: UserValidator
) {

    companion object {
        val instance: UserCreator = UserCreator(UserRepositoryImpl.instance, UserValidator.instance)
    }

    fun run(user: User): User {
        userValidator.validatorEmail(email = user.email)
        return userOpsRepository.save(user)
    }
}
