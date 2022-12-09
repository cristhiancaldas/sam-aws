package app.bank.user.application

import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl
import app.bank.shared.UserDto

class UserUpdater(
    private val userOpsRepository: UserRepository,
    private val userValidator: UserValidator,
    private val userReader: UserReader
) {

    companion object {
        val instance: UserUpdater =
            UserUpdater(UserRepositoryImpl.instance, UserValidator.instance, UserReader.instance)
    }

    fun run(idUser: Long, userDTO: UserDto) {
        userValidator.validatorEmail(userDTO.email)
        val user = userReader.getUser(idUser)
        user.firstName =userDTO.firstName
        user.lastName = userDTO.lastName
        user.email = userDTO.email
        user.phone = userDTO.phone
        user.country = userDTO.country
        user.region = userDTO.region
        userOpsRepository.save(listOf(user))
    }
}