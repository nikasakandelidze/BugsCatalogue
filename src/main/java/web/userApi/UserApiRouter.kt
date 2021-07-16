package web.userApi

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class UserApiRouter {
    fun getRouter(vertx: Vertx): Router {
        val router: Router = Router.router(vertx)
        router.get("/data")
            .handler {
                it.end("this is data endpoint!")
            }
        return router
    }
}