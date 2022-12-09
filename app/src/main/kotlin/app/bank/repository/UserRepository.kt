package app.bank.repository

import app.bank.bean.User
import app.bank.config.UserFilter

interface UserRepository {

    fun getUser(id: Long): User

    fun save(user: User): User

    fun save(users: List<User>): Int
    fun getUsers(userFilter: UserFilter): List<User>

    fun warMDdbConnection()
}

class UserNotFoundException(msg: Long?) : RuntimeException("User could not be found : $msg")

class SaveUserException(msg: String?) : RuntimeException("${"User could not be saved"}: $msg")
class UsersNotFoundException(msg: String?) : RuntimeException("Users could not be found : $msg")
