package app.bank.user.application

import app.bank.bean.User
import app.bank.repository.impl.UserRepositoryImpl
import app.bank.repository.UserRepository

class UserReader(
    private val userOpsRepository: UserRepository
) {

    companion object {
        val instance: UserReader = UserReader(UserRepositoryImpl.instance)
    }

    fun run(idUser: Long): User {
        return userOpsRepository.getUser(idUser)
    }
}
