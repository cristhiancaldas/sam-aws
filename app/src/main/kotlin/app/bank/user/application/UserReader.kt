package app.bank.user.application

import app.bank.bean.User
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl

class UserReader(
    private val userOpsRepository: UserRepository
) {
    companion object {
        val instance: UserReader = UserReader(UserRepositoryImpl.instance)
    }
    fun getUser(idUser: Long): User {
        return userOpsRepository.getUser(idUser)
    }

    fun getUsers(): List<User> {
        return userOpsRepository.getUsers()
    }
}
