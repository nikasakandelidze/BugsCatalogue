package web.adminApi

import common.eventbus.AdminApiVerticleAddress
import domain.Topic
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import web.adminApi.validator.AdminInputValidator
import web.utils.ResponseUtils

class AdminApiRouter(val adminInputValidator: AdminInputValidator) {
    fun getRouter(vertx: Vertx): Router {
        val router: Router = Router.router(vertx)
        router.get("/info")
            .handler {
                it.end("info endpoint!")
            }
        router.post("/topic")
            .handler { handPostTopicRequest(it, vertx) }
        return router
    }

    private fun handPostTopicRequest(routingContext: RoutingContext, vertx: Vertx) {
        val body: JsonObject = routingContext.bodyAsJson
        val topic = body.mapTo(Topic::class.java)
        val validateNewTopic = adminInputValidator.validateNewTopic(topic)
        val httpResponse = routingContext.response()
        if (validateNewTopic.isValid) {
            vertx.eventBus().publish(AdminApiVerticleAddress.topicsDispatcher, JsonObject.mapFrom(topic))
            ResponseUtils.respondWithOkResponse(httpResponse, "successfully added new topic")
        } else {
            ResponseUtils.respondWithBadRequest(httpResponse, validateNewTopic.messages)
        }
    }
}