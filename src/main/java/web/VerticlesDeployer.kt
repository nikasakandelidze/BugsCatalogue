package web

import dispatcher.openApi.OpenApiMessageAggregatorVerticle
import dispatcher.openApi.OpenApiMessageDispatcherVerticle
import dispatcher.openApi.validator.OpenApiMessageValidator
import io.vertx.core.Vertx
import storage.topics.TopicStorage

fun main(args: Array<String>) {
    val vertx = Vertx.vertx();
    vertx.deployVerticle(ServerVerticle())
    val topicStorage = TopicStorage()
    vertx.deployVerticle(OpenApiMessageDispatcherVerticle(OpenApiMessageValidator(), topicStorage))
    vertx.deployVerticle(OpenApiMessageAggregatorVerticle(topicStorage))
}