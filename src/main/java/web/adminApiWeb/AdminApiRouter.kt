package web.adminApiWeb

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class AdminApiRouter {
    fun getRouter(vertx: Vertx): Router {
        val router: Router = Router.router(vertx)
        router.get("/info")
            .handler {
                it.end("info endpoint!")
            }
        return router
    }
}