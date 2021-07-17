package service.openApi.validator

import common.validator.ValidationResult
import io.vertx.core.json.JsonObject

class OpenApiMessageValidator {
    fun validateOpenApiMessage(message: JsonObject): ValidationResult {
        return ValidationResult(false, mutableSetOf())
    }
}