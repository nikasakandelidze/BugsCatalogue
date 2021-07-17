package service.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import org.apache.log4j.LogManager
import service.openApi.validator.OpenApiMessageValidator
import storage.IBugsStorage
import web.dto.BugsFilterRequest
import java.lang.Exception

class OpenApiServiceVerticle(val openApiMessageValidator: OpenApiMessageValidator, val bugsStorage: IBugsStorage) :
    AbstractVerticle() {
    val logger = LogManager.getLogger(OpenApiServiceVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer(OpenApiVerticleAddress.listBugsAddress, this::handleBugsListing)
        super.start(startPromise)
    }

    private fun handleBugsListing(message: Message<JsonObject>) {
        try {
            val body = message.body().mapTo(BugsFilterRequest::class.java)
            val validationResult = openApiMessageValidator.validateOpenApiMessage(body)
            if (validationResult.isValid) {
                val bugs = bugsStorage.filterBugs()
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