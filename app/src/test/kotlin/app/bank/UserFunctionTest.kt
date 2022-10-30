package app.bank
import app.bank.functions.UserFunction
import app.bank.shared.UserDto
import app.bank.user.application.UserCreator
import app.bank.user.application.UserReader
import io.mockk.mockk

internal class UserFunctionTest {

    private val userCreator = mockk<UserCreator>()
    private val userReader = mockk<UserReader>()
    private val userFunction = UserFunction(userCreator, userReader)


    private fun userDTOTest(): UserDto {
        return UserDto(
            id = 859,
            firstName = "Cristhian",
            lastName = "Caldas Mendoza",
            email = "c.caldas.m@gmail.com",
            phone = "985471258",
            country = "PERU",
            region = "Lima"
        )
    }
}
