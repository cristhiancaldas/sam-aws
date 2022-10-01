package app.bank.shared

import app.bank.bean.User

class UserConverter {

    companion object {
        val instance = UserConverter()
    }

    fun convert(from: UserDto): User {
        return User(
            id = from.id,
            firstName = from.firstName,
            lastName = from.lastName,
            email = from.email,
            phone = from.phone,
            country = from.country,
            region = from.region
        )
    }
}
