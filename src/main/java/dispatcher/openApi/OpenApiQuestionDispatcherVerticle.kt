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
import storage.question.QuestionStorage
import storage.topics.TopicStorage
import web.dto.QuestionDispatchRequest
import java.lang.Exception
import java.util.*

class OpenApiQuestionDispatcherVerticle(
    private val openApiServiceQuestionValidator: OpenApiMessageValidator,
    private val topicStorage: TopicStorage,
    private val questionStorage: QuestionStorage
) :
    AbstractVerticle() {
    val logger = LogManager.getLogger(OpenApiQuestionDispatcherVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer<JsonObject>(OpenApiVerticleAddress.questionDispatcher, this::handleQuestionDispatch)
        super.start(startPromise)
    }

    private fun handleQuestionDispatch(message: Message<JsonObject>) {
        try {
            val body: QuestionDispatchRequest = message.body().mapTo(QuestionDispatchRequest::class.java)
            val validationResult = openApiServiceQuestionValidator.validateOpenApiQuestion(body)
            if (validationResult.isValid) {
                val question = Question(id = -1, body.email!!, body.title!!, body.content!!, true)
                questionStorage.addNewQuestion(question, body.topicId!!)
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