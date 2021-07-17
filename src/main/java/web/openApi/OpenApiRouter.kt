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
            .handler(this::handleListBugsRequest)
        return openApiRouter
    }

    private fun handleListBugsRequest(routingContext: RoutingContext) {
        val requestBody = routingContext.bodyAsJson
        val dto = requestBody.mapTo(BugsFilterRequest::class.java)
        val result = openApiRequestValidator.validateBugsListRequest(dto)
        if (result.isValid) {
            vertx.eventBus().request<JsonObject>(
                OpenApiVerticleAddress.listBugsAddress,
                JsonObject.mapFrom(dto),
                this::handleResponse
            )
        } else {
            ResponseUtils.respondWithBadRequest(routingContext.response(), result.messages)
        }
    }

    private fun handleLoginRequest(routingContext: RoutingContext) {
        routingContext.end("login endpoint!")
    }

    private fun handleResponse(result: AsyncResult<Message<JsonObject>>) {

    }
}