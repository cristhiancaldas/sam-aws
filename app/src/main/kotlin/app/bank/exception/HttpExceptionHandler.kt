package app.bank.exception

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent

class HttpExceptionHandler {

    companion object {
        val instance: HttpExceptionHandler = HttpExceptionHandler()
    }

    fun handle(exception: Throwable, context: Context): APIGatewayProxyResponseEvent {
        return APIGatewayProxyResponseEvent()
            .withStatusCode(getHttpStatus(exception, context))
            .withBody(getBody(exception))
    }

    private fun getHttpStatus(exception: Throwable, context: Context): Int {
        context.logger.log(exception.stackTraceToString())
        return 500
    }

    private fun getBody(exception: Throwable): String {
        return "{\"message\" : \"${exception.message}\" }"
    }
}
