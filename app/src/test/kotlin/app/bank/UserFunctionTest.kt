package app.bank

import app.bank.bean.User
import app.bank.exception.RestPaths
import app.bank.functions.UserFunction
import app.bank.shared.UserDto
import app.bank.user.application.UserCreator
import app.bank.user.application.UserReader
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import org.junit.Test
import kotlin.test.assertEquals

internal class UserFunctionTest {

    private val userCreator = mockk<UserCreator>()
    private val userReader = mockk<UserReader>()
    private val userFunction = UserFunction(userCreator, userReader)

    @Test
    fun handlerRequestPostRestEvaluationReturnStatusOk() {

        mockkObject(UserCreator.Companion)
        every { UserCreator.Companion.instance } answers { userCreator }

        val userDTO= Json.encodeToString(userDTOTest())
        every { userCreator.run(any()) } answers { User(id = 95874) }
        val apiGateway = APIGatewayProxyRequestEvent()
        apiGateway.body = userDTO
        apiGateway.resource = RestPaths.USER.path
        apiGateway.path = RestPaths.USER.path
        apiGateway.httpMethod = "POST"
        apiGateway.requestContext = APIGatewayProxyRequestEvent.ProxyRequestContext()
        val handle = userFunction.handleRequest(apiGateway, TestAWSContext("UserFunction"))
        assertEquals(201, handle.statusCode)

    }

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