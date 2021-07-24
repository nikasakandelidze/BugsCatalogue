package web.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import web.dto.QuestionDispatchRequest
import web.openApi.validator.OpenApiRequestValidator
import web.utils.ResponseUtils

class OpenApiRouter(private val openApiRequestValidator: OpenApiRequestValidator, val vertx: Vertx) {


    fun getRouter(vertx: Vertx): Router {
        val openApiRouter = Router.router(vertx)
        openApiRouter.get("/login")
            .handler(this::handleLoginRequest)
        openApiRouter.get("/topic")
            .handler { handleFilterTopicsRequest(it) }
        openApiRouter.post("/topic/:topicId/question")
            .handler { handleDispatchNewQuestionForTopicRequest(it) }
        return openApiRouter
    }


    private fun handleFilterTopicsRequest(routingContext: RoutingContext) {
        vertx.eventBus().request<JsonObject>(
            OpenApiVerticleAddress.topicsAggregator,
            JsonObject()
        ) { handleResponse(it, routingContext) }
    }


    private fun handleDispatchNewQuestionForTopicRequest(routingContext: RoutingContext) {
        val requestBody: JsonObject? = routingContext.bodyAsJson
        requestBody?.let {
            val dto = it.mapTo(QuestionDispatchRequest::class.java)
            val topicId: Int? = routingContext.pathParam("topicId").toIntOrNull()
            dto.topicId = topicId
            val result = openApiRequestValidator.validateDistpachNewQuestionRequest(dto)
            if (result.isValid) {
                vertx.eventBus().request<JsonObject>(
                    OpenApiVerticleAddress.questionDispatcher,
                    JsonObject.mapFrom(dto)
                ) { handleResponse(it, routingContext) }
            } else {
                ResponseUtils.respondWithBadRequest(routingContext.response(), result.messages)
            }
        }
    }

    private fun handleLoginRequest(routingContext: RoutingContext) {
        vertx.executeBlocking<JsonObject> {
            Thread.sleep(500)
            routingContext.end("login endpoint!")
        }
    }

    private fun handleResponse(result: AsyncResult<Message<JsonObject>>, routingContext: RoutingContext) {
        if (result.succeeded()) {
            val body = result.result().body()
            val response = body.getJsonArray("items")
            routingContext.response()
                .setStatusCode(200)
                .putHeader("Content-Type", "application/json")
                .end(json { body }.toString())
        } else {
            ResponseUtils.respondWithServersideProblem(
                routingContext.response(),
                mutableSetOf(result.cause().message.orEmpty())
            )
        }
    }
}