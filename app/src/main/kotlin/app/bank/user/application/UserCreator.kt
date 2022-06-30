package app.bank.user.application

import app.bank.bean.User
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl
import app.bank.shared.UserConverter
import app.bank.shared.UserDto

class UserCreator (
    private val userOpsRepository: UserRepository
) {

    companion object {
        val instance: UserCreator = UserCreator(UserRepositoryImpl.instance)
    }

    fun run(userDto: UserDto): User? {
        val user = UserConverter.instance.convert(userDto)
        return userOpsRepository.save(user)
    }
}