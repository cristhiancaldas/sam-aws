package app.bank.functions
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

open class BaseFunction {

    inline fun <reified T> getPathParameterValue(event: APIGatewayProxyRequestEvent, pathParameterName: String): T {
        val pathParameterValue = event.pathParameters[pathParameterName]!!
        return when (T::class) {
            Boolean::class -> (pathParameterValue.toBooleanStrict()) as T
            Int::class -> pathParameterValue.toInt() as T
            Long::class -> pathParameterValue.toLong() as T
            else -> pathParameterValue as T
        }
    }

    inline fun <reified T> getQueryParameterValue(event: APIGatewayProxyRequestEvent, queryParam: String): T {
        var paramValue = event.queryStringParameters?.let { event.queryStringParameters[queryParam] }
        return when (T::class) {
            Boolean::class -> (paramValue?.lowercase()?.toBooleanStrict() ?: false) as T
            Int::class -> paramValue?.toInt() as T
            Long::class -> paramValue?.toLong() as T
            Array<Long>::class -> getArray(paramValue) as T
            else -> paramValue as T
        }
    }

    fun getArray(paramValue: String?): Array<Long> {
        return if (paramValue.isNullOrEmpty()) {
            arrayOf()
        } else {
            paramValue.split(",").map { it.toLong() }.toTypedArray()
        }
    }
}
