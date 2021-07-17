package service.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import service.openApi.validator.OpenApiMessageValidator
import web.dto.BugsFilterRequest

class OpenApiServiceVerticle(val openApiMessageValidator: OpenApiMessageValidator) : AbstractVerticle() {
    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer(OpenApiVerticleAddress.listBugsAddress, this::handleBugsListing)
        super.start(startPromise)
    }

    private fun handleBugsListing(message: Message<JsonObject>) {
        val body = message.body().mapTo(BugsFilterRequest::class.java)
        val validationResult = openApiMessageValidator.validateOpenApiMessage(body)
        if (validationResult.isValid) {

        } else {
            message.reply(JsonObject.mapFrom(validationResult))
        }
    }
}