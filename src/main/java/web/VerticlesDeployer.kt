package web

import io.vertx.core.Vertx
import dispatcher.openApi.OpenApiMessageDispatcherVerticle
import dispatcher.openApi.validator.OpenApiMessageValidator
import storage.message.MessageStorage

fun main(args: Array<String>) {
    val vertx = Vertx.vertx();
    vertx.deployVerticle(ServerVerticle())
    vertx.deployVerticle(OpenApiMessageDispatcherVerticle(OpenApiMessageValidator(), MessageStorage()))
}