package app.bank.repository

import app.bank.bean.User

interface UserRepository {

    fun getUser(id: Long): User

    fun save(user: User): User?
}

class UserNotFoundException(msg: Long?) : RuntimeException("User could not be found : $msg")

class SaveUserException(msg: String?) : RuntimeException("${"User could not be saved"}: $msg")
