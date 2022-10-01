package app.bank.shared

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
class UserDto(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var country: String,
    var region: String
) {
    companion object {
        fun from(body: String): UserDto {
            return Json { ignoreUnknownKeys = true }.decodeFromString(body)
        }
    }
}
