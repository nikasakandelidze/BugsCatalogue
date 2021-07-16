package web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import web.adminApiWeb.AdminApiRouter
import web.openApiWeb.OpenApiRouter

class ServerVerticle : AbstractVerticle() {

    private val openApiRouter: OpenApiRouter = OpenApiRouter()
    private val adminApiRouter: AdminApiRouter = AdminApiRouter()

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
        return rootRouter
    }
}