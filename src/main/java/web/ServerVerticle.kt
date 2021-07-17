package web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import web.adminApi.AdminApiRouter
import web.openApi.OpenApiRouter
import web.openApi.validator.OpenApiRequestValidator
import web.userApi.UserApiRouter

class ServerVerticle : AbstractVerticle() {

    private val openApiRouter: OpenApiRouter = OpenApiRouter(OpenApiRequestValidator(), vertx)
    private val adminApiRouter: AdminApiRouter = AdminApiRouter()
    private val userApiRouter: UserApiRouter = UserApiRouter()

    override fun start(startPromise: Promise<Void>?) {
        val httpServer = vertx.createHttpServer()
        val port = 8080
        httpServer.requestHandler(setupRootRouter())
            .listen(port)
        super.start(startPromise)
    }

    private fun setupRootRouter(): Router {
        val rootRouter = Router.router(vertx)
        rootRouter.mountSubRouter("/open-api", openApiRouter.getRouter(vertx))
        rootRouter.mountSubRouter("/admin-api", adminApiRouter.getRouter(vertx))
        rootRouter.mountSubRouter("/user-api", userApiRouter.getRouter(vertx))
        return rootRouter
    }
}