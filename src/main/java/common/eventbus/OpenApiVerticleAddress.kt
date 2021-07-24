package common.eventbus

class OpenApiVerticleAddress {
    companion object {
        val topicsDispatcher: String = "openapi.dispatch.topic"
        val topicsAggregator: String = "openapi.fetch.topic"
    }
}