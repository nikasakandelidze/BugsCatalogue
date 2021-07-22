package dispatcher.openApi.validator

import common.validator.ValidationResult
import web.dto.MessageRequest

class OpenApiMessageValidator {
    fun validateOpenApiMessage(message: MessageRequest): ValidationResult {
        return ValidationResult(true, mutableSetOf())
    }
}