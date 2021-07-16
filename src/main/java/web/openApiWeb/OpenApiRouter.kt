package web.openApiWeb

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class OpenApiRouter {

    fun getRouter(vertx: Vertx): Router {
        val openApiRouter = Router.router(vertx)
        openApiRouter.get("/login")
            .handler {
                it.end("login endpoint!")
            }
        return openApiRouter
    }
}