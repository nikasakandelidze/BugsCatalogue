package web.openApi

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class OpenApiRouter {

    fun getRouter(vertx: Vertx): Router {
        val openApiRouter = Router.router(vertx)
        openApiRouter.get("/login")
            .handler {
                it.end("login endpoint!")
            }
        openApiRouter.get("/list-bugs")
            .handler {

            }
        return openApiRouter
    }
}