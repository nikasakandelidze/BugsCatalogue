package web

import dispatcher.adminApi.AdminApiMessageAggregatorVerticle
import dispatcher.adminApi.validator.TopicDispatchValidator
import dispatcher.openApi.OpenApiTopicsAggregatorVerticle
import dispatcher.openApi.OpenApiQuestionDispatcherVerticle
import dispatcher.openApi.validator.OpenApiMessageValidator
import io.vertx.core.Vertx
import storage.dbInitializer.JdbcInitializer
import storage.question.QuestionStorage
import storage.topics.TopicStorage

fun main(args: Array<String>) {
    val vertx = Vertx.vertx();
    vertx.deployVerticle(ServerVerticle())
    val jdbcClient = JdbcInitializer().init(vertx, 5433, "elarge")
    val topicStorage = TopicStorage(jdbcClient)
    val questionStorage = QuestionStorage(jdbcClient)
    vertx.deployVerticle(OpenApiQuestionDispatcherVerticle(OpenApiMessageValidator(), topicStorage, questionStorage))
    vertx.deployVerticle(OpenApiTopicsAggregatorVerticle(topicStorage))
    vertx.deployVerticle(AdminApiMessageAggregatorVerticle(topicStorage, TopicDispatchValidator()))
}