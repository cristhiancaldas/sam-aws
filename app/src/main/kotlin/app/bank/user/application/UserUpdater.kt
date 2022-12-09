package app.bank.user.application

import app.bank.config.LoggerDelegate
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl
import app.bank.shared.UserDto

class UserUpdater(
    private val userOpsRepository: UserRepository,
    private val userValidator: UserValidator,
    private val userReader: UserReader
) {
    private val log by LoggerDelegate()
    companion object {
        val instance: UserUpdater =
            UserUpdater(UserRepositoryImpl.instance, UserValidator.instance, UserReader.instance)
    }

    fun run(idUser: Long, userDTO: UserDto) {
        val user = userReader.getUser(idUser)
        if(user.email != userDTO.email){
            log.info("email repetido ${userDTO.email}")
            userValidator.validatorEmail(userDTO.email)
        }
        user.firstName =userDTO.firstName
        user.lastName = userDTO.lastName
        user.email = userDTO.email
        user.phone = userDTO.phone
        user.country = userDTO.country
        user.region = userDTO.region
        userOpsRepository.save(listOf(user))
    }
}