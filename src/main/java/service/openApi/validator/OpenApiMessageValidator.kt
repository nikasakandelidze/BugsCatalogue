package service.openApi.validator

import common.validator.ValidationResult
import io.vertx.core.json.JsonObject
import web.dto.BugsFilterRequest

class OpenApiMessageValidator {
    fun validateOpenApiMessage(message: BugsFilterRequest): ValidationResult {
        return ValidationResult(false, mutableSetOf())
    }
}