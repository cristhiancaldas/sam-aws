package app.bank.user.application

import app.bank.bean.User
import app.bank.config.LoggerDelegate
import app.bank.config.UserFilter
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl

class UserReader(
    private val userRepository: UserRepository
) {
    private val log by LoggerDelegate()

    companion object {
        val instance: UserReader = UserReader(UserRepositoryImpl.instance)
    }
    fun getUser(idUser: Long): User {
        return userRepository.getUser(idUser)
    }

    fun getUsers(): List<User> {
        val filter = UserFilter(active = "1")
        log.info(filter.toString())
        return userRepository.getUsers(filter)
    }
}
