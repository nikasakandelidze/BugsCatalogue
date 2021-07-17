package web.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import web.dto.BugsFilterRequest
import web.openApi.validator.OpenApiRequestValidator
import web.utils.ResponseUtils
import java.net.http.HttpResponse

class OpenApiRouter(val openApiRequestValidator: OpenApiRequestValidator, val vertx: Vertx) {


    fun getRouter(vertx: Vertx): Router {
        val openApiRouter = Router.router(vertx)
        openApiRouter.get("/login")
            .handler(this::handleLoginRequest)
        openApiRouter.get("/bugs-filter")
            .handler { handleListBugsRequest(it) }
        return openApiRouter
    }

    private fun handleListBugsRequest(routingContext: RoutingContext) {
        val requestBody = routingContext.bodyAsJson
        val dto = requestBody.mapTo(BugsFilterRequest::class.java)
        val result = openApiRequestValidator.validateBugsListRequest(dto)
        if (result.isValid) {
            vertx.eventBus().request<JsonObject>(
                OpenApiVerticleAddress.listBugsAddress,
                JsonObject.mapFrom(dto)
            ) { handleResponse(it, routingContext) }
        } else {
            ResponseUtils.respondWithBadRequest(routingContext.response(), result.messages)
        }
    }

    private fun handleLoginRequest(routingContext: RoutingContext) {
        routingContext.end("login endpoint!")
    }

    private fun handleResponse(result: AsyncResult<Message<JsonObject>>, routingContext: RoutingContext) {
        if (result.succeeded()) {
            val body = result.result().body()
            val response = body.getJsonArray("bugs")
            routingContext.response()
                .setStatusCode(200)
                .end(json { response }.toString())
        } else {
            ResponseUtils.respondWithServersideProblem(
                routingContext.response(),
                mutableSetOf(result.cause().message.orEmpty())
            )
        }
    }
}