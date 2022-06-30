package app.bank.functions

import app.bank.common.created
import app.bank.common.methodNotAllowed
import app.bank.common.notFound
import app.bank.common.ok
import app.bank.config.LoggerDelegate
import app.bank.shared.UserDto
import app.bank.exception.HttpExceptionHandler
import app.bank.exception.RestPaths
import app.bank.user.application.UserCreator
import app.bank.user.application.UserReader
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserFunction : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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
        when (event.resource) {
            RestPaths.USER -> getFilter(event)
            else -> {
                notFound(event.path + "" + event.resource)
            }
        }

    private fun getFilter(event: APIGatewayProxyRequestEvent) =
        when (event.httpMethod) {
            "GET"  -> getUser(event)
            "POST" -> addUser(event)
            else -> methodNotAllowed()
        }

    private fun getUser(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val id = event.queryStringParameters["id"]!!
        val user = UserReader.instance.run(id.toLong())
        return ok(Json.encodeToString(user))
    }

    private fun addUser(event: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val userDto = UserDto.from(event.body)
        var user= UserCreator.instance.run(userDto)
        return created(Json.encodeToString(user))
    }
}
