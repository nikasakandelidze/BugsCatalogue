package web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import web.adminApi.AdminApiRouter
import web.openApi.OpenApiRouter
import web.openApi.validator.OpenApiRequestValidator
import web.userApi.UserApiRouter
import web.utils.RequestUtils

class ServerVerticle : AbstractVerticle() {

    private lateinit var openApiRouter: OpenApiRouter
    private lateinit var adminApiRouter: AdminApiRouter
    private lateinit var userApiRouter: UserApiRouter
    private val port = 8080

    override fun start(startPromise: Promise<Void>?) {
        val httpServer = vertx.createHttpServer()
        setupSubRouters()
        httpServer.requestHandler(setupRootRouter())
            .listen(port)
        super.start(startPromise)
    }

    private fun setupSubRouters() {
        openApiRouter = OpenApiRouter(OpenApiRequestValidator(), vertx)
        adminApiRouter = AdminApiRouter()
        userApiRouter = UserApiRouter()
    }

    private fun setupRootRouter(): Router {
        val rootRouter = Router.router(vertx)
        rootRouter.route()
            .handler(BodyHandler.create())
            .handler { RequestUtils.validateRequest(it) }
        rootRouter.mountSubRouter("/open-api", openApiRouter.getRouter(vertx))
        rootRouter.mountSubRouter("/admin-api", adminApiRouter.getRouter(vertx))
        rootRouter.mountSubRouter("/user-api", userApiRouter.getRouter(vertx))
        return rootRouter
    }
}