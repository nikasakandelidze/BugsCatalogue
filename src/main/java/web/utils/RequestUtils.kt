package web.utils

import io.vertx.ext.web.RoutingContext

class RequestUtils {
    companion object {
        fun validateRequest(routingContext: RoutingContext) {
            val httpRequest = routingContext.request()
            val contentType = httpRequest.getHeader("Content-Type")
            val accept = httpRequest.getHeader("Accept")
            if (contentType == null || accept == null || !contentType.equals("application/json")) {
                ResponseUtils.respondWithBadRequest(
                    routingContext.response(),
                    mutableSetOf("Content-Type: application/json and Accept: [application/json, */*] headers are mandatory.")
                )
            } else {
                routingContext.next()
            }
        }
    }
}