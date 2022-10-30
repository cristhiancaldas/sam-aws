package app.bank.common

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent

fun ok(body: String? = ""): APIGatewayProxyResponseEvent =
    APIGatewayProxyResponseEvent()
        .withStatusCode(200)
        .withHeaders(mapOf("Content-Type" to "application/json"))
        .withBody(body)

fun badRequest(body: String) =
    APIGatewayProxyResponseEvent()
        .withStatusCode(400)
        .withBody(body)!!

fun methodNotAllowed() =
    APIGatewayProxyResponseEvent()
        .withStatusCode(405)!!

fun accessDenied() =
    APIGatewayProxyResponseEvent()
        .withStatusCode(401)!!

fun forbidden(body: String) =
    APIGatewayProxyResponseEvent()
        .withStatusCode(403)
        .withBody(body)!!

fun notFound(path: String) =
    APIGatewayProxyResponseEvent()
        .withStatusCode(404)
        .withBody(path)!!

fun internalServerError(message: String?) =
    APIGatewayProxyResponseEvent()
        .withStatusCode(500)
        .withBody(message)!!

fun created(body: String? = ""): APIGatewayProxyResponseEvent =
    APIGatewayProxyResponseEvent()
        .withStatusCode(201)
        .withHeaders(mapOf("Content-Type" to "application/json"))
        .withBody(body)
