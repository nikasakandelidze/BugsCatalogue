package web.utils

import io.vertx.core.http.HttpServerResponse
import io.vertx.kotlin.core.json.json

class ResponseUtils {
    companion object {

        fun respondWithBadRequest(httpResponse: HttpServerResponse, message: MutableSet<String>) {
            httpResponse
                .setStatusCode(400)
                .end(json { message }.toString())
        }

        fun respondWithServersideProblem(httpResponse: HttpServerResponse, message: MutableSet<String>) {
            httpResponse
                .setStatusCode(500)
                .end(json { message }.toString())
        }
    }
}