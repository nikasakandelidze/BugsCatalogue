package dispatcher.adminApi

import common.eventbus.AdminApiVerticleAddress
import dispatcher.adminApi.validator.TopicDispatchValidator
import domain.Topic
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import storage.topics.TopicStorage

class AdminApiMessageAggregatorVerticle(
    val topicStorage: TopicStorage,
    val topicDispatchValidator: TopicDispatchValidator
) : AbstractVerticle() {
    override fun start(startPromise: Promise<Void>?) {
        vertx.eventBus().consumer<JsonObject>(AdminApiVerticleAddress.topicsDispatcher, this::handleTopicDispatch)
        super.start(startPromise)
    }

    private fun handleTopicDispatch(message: Message<JsonObject>) {
        val topic = message.body().mapTo(Topic::class.java)
        val validateNewTopic = topicDispatchValidator.validateNewTopic(topic)
        if(validateNewTopic.isValid){
            topicStorage.addNewTopic(topic)
        }else{

        }
    }
}