package dispatcher.openApi.validator

import common.validator.ValidationResult
import web.dto.SendMessageRequest

class OpenApiMessageValidator {
    fun validateOpenApiMessage(message: SendMessageRequest): ValidationResult {
        return ValidationResult(true, mutableSetOf())
    }
}