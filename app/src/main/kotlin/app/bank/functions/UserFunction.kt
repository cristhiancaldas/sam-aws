package app.bank.functions

import app.bank.common.methodNotAllowed
import app.bank.common.notFound
import app.bank.common.ok
import app.bank.config.LoggerDelegate
import app.bank.exception.HttpExceptionHandler
import app.bank.exception.RestPaths
import app.bank.exception.RestPaths.*
import app.bank.shared.UserDto
import app.bank.user.application.UserCreator
import app.bank.user.application.UserReader
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserFunction(
    private val userCreator: UserCreator = UserCreator.instance,
    private val userReader: UserReader = UserReader.instance
) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>, BaseFunction() {

    private val log by LoggerDelegate()

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        log.error("Start handleRequest with event: $input and body ${input.body}")
        return try {
            handlerEvent(input)
        } catch (ex: Exception) {
            HttpExceptionHandler.instance.handle(ex, context)
        }
    }

    private fun handlerEvent(event: APIGatewayProxyRequestEvent) =
        when (event.httpMethod) {
            "GET" -> doGet(event)
            "POST" -> doPost(event)
            else -> methodNotAllowed()
        }

    private fun doPut(event: APIGatewayProxyRequestEvent) =
        when (RestPaths.getPath(event.resource)) {
            EDIT_USER -> updateUser(event)
            else -> {
                notFound(event.path + "" + event.resource)
            }
        }

    private fun doGet(event: APIGatewayProxyRequestEvent) =
        when (RestPaths.getPath(event.resource)) {
            GET_USER -> getUser(event)
            LIST_USERS -> getUsers()
            VALIDATE -> getValidate(event)
            else -> {
                notFound(event.path + "" + event.resource)
            }
        }

    private fun doPost(event: APIGatewayProxyRequestEvent) =
        when (RestPaths.getPath(event.resource)) {
            ADD_USER -> addUser(event)
            else -> {
                notFound(event.path + "" + event.resource)
            }
        }

    private fun getUsers(): APIGatewayProxyResponseEvent {
        val user = userReader.getUsers()
        return ok(Json.encodeToString(user))
    }

    private fun getUser(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val id = getQueryParameterValue<Long>(event, "id")
        val user = userReader.getUser(id)
        return ok(Json.encodeToString(user))
    }

    private fun updateUser(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        return ok()
    }

    private fun addUser(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val userDto = UserDto.from(event.body)
        val user = userCreator.run(userDto)
        return ok(Json.encodeToString(user))
    }

    private fun getValidate(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val messages = getPathParameterValue<String>(event, "messages")
        val message = "Service Operative - 2022 - $messages"
        return ok(message)
    }
}
