package dispatcher.openApi

import common.eventbus.OpenApiVerticleAddress
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import storage.topics.TopicStorage

class OpenApiMessageAggregatorVerticle(private val topicStorage: TopicStorage) : AbstractVerticle() {
    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer(OpenApiVerticleAddress.topicsAggregator, this::handleTopicFetch)
        super.start(startPromise)
    }

    private fun handleTopicFetch(message: Message<JsonObject>) {
        topicStorage.getAllTopics {
            val toCollection = it.map { JsonObject.mapFrom(it) }
                .toCollection(mutableListOf())
            message.reply(JsonObject().put("items", toCollection))
        }
    }
}