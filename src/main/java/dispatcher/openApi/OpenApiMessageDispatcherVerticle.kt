package dispatcher.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import org.apache.log4j.LogManager
import dispatcher.openApi.validator.OpenApiMessageValidator
import storage.message.IMessageStorage
import web.dto.SendMessageRequest
import java.lang.Exception

class OpenApiMessageDispatcherVerticle(
    val openApiServiceMessageValidator: OpenApiMessageValidator,
    val messageStorage: IMessageStorage
) :
    AbstractVerticle() {
    val logger = LogManager.getLogger(OpenApiMessageDispatcherVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer(OpenApiVerticleAddress.messageDispatcher, this::handleMessageDispatch)
        super.start(startPromise)
    }

    private fun handleMessageDispatch(message: Message<JsonObject>) {
        try {
            val body = message.body().mapTo(SendMessageRequest::class.java)
            val validationResult = openApiServiceMessageValidator.validateOpenApiMessage(body)
            if (validationResult.isValid) {
                val bugs = messageStorage.addUnauthorizedMessageToTopic(domain.Message())
                message.reply(json { bugs })
            } else {
                message.reply(JsonObject.mapFrom(validationResult))
            }
        } catch (e: Exception) {
            logger.warn(e)
            message.fail(500, e.message)
        }
    }
}