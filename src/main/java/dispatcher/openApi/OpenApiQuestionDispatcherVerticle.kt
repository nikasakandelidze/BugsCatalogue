package dispatcher.openApi

import common.eventbus.OpenApiVerticleAddress
import dispatcher.messageDispatcher.IMessageDispatcher
import dispatcher.messageDispatcher.PushMessage
import dispatcher.openApi.validator.OpenApiMessageValidator
import domain.Question
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import org.apache.log4j.LogManager
import storage.question.QuestionStorage
import storage.user.UserStorage
import web.dto.QuestionDispatchRequest

class OpenApiQuestionDispatcherVerticle(
    private val openApiServiceQuestionValidator: OpenApiMessageValidator,
    private val questionStorage: QuestionStorage,
    private val messageDispatcher: IMessageDispatcher,
    private val userStorage: UserStorage
) :
    AbstractVerticle() {
    private val logger = LogManager.getLogger(OpenApiQuestionDispatcherVerticle::class.java)

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
                questionStorage.addNewQuestion(
                    question,
                    body.topicId!!
                ) { pushMessagesToTopicSubscribers(body.topicId!!, question) }

                message.reply(json { JsonObject.mapFrom(question) })
            } else {
                message.reply(JsonObject.mapFrom(validationResult))
            }
        } catch (e: Exception) {
            logger.warn(e)
            message.fail(500, e.message)
        }
    }

    private fun pushMessagesToTopicSubscribers(topicId: Int, question: Question) {
        userStorage.getAllUsersSubscribedToTopicWithIdOf(topicId) {
            it.forEach {
                messageDispatcher.sendMessage(
                    PushMessage(
                        question.content!!,
                        it.email,
                        question.email!!,
                        question.title!!
                    )
                ) { logger.info("Sent email about question to ${it.to}") }
            }
        }
    }
}