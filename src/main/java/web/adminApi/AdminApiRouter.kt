package web.adminApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import web.dto.MessageRequest
import web.utils.ResponseUtils

class AdminApiRouter {
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
    }
}