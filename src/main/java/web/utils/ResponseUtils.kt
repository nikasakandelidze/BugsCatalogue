package web.utils

import io.vertx.core.http.HttpServerResponse

class ResponseUtils {
    companion object {

        fun respondWithBadRequest(httpResponse: HttpServerResponse, message: MutableSet<String>) {
            httpResponse
                .setStatusCode(400)
                .end(message.toString())
        }
    }
}