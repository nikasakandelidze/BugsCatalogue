package dispatcher.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import org.apache.log4j.LogManager
import dispatcher.openApi.validator.OpenApiMessageValidator
import domain.Question
import storage.topics.TopicStorage
import web.dto.MessageRequest
import java.lang.Exception
import java.util.*

class OpenApiMessageDispatcherVerticle(
    private val openApiServiceMessageValidator: OpenApiMessageValidator,
    private val topicStorage: TopicStorage
) :
    AbstractVerticle() {
    val logger = LogManager.getLogger(OpenApiMessageDispatcherVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer(OpenApiVerticleAddress.topicsDispatcher, this::handleMessageDispatch)
        super.start(startPromise)
    }

    private fun handleMessageDispatch(message: Message<JsonObject>) {
        try {
            val body: MessageRequest = message.body().mapTo(MessageRequest::class.java)
            val validationResult = openApiServiceMessageValidator.validateOpenApiMessage(body)
            if (validationResult.isValid) {
                val question = Question(UUID.randomUUID().toString(), body.email!!, body.title!!, body.content!!, true)
                topicStorage.addQuestionToTopic(question, body.topicId!!)
                message.reply(json { JsonObject.mapFrom(question) })
            } else {
                message.reply(JsonObject.mapFrom(validationResult))
            }
        } catch (e: Exception) {
            logger.warn(e)
            message.fail(500, e.message)
        }
    }
}