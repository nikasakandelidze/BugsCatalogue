package web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import org.apache.log4j.Logger

class ServerVerticle : AbstractVerticle() {
    private val LOGGER = Logger.getLogger(ServerVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        val httpServer = vertx.createHttpServer()
        val port = 8080
        httpServer.requestHandler(setupRouter())
            .listen(port)
        super.start(startPromise)
        LOGGER.info("Started HTTP Server on port ${port}")
    }

    private fun setupRouter(): Router {
        val rootRouter = Router.router(vertx)
        rootRouter.route("/hi").handler {
            val response = it.response()
            LOGGER.info("Recieved request in vertx")
            response.end("Hi from vertx!")
        }
        return rootRouter
    }
}