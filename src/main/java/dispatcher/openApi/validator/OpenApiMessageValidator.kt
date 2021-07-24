package dispatcher.openApi.validator

import common.validator.ValidationResult
import web.dto.QuestionDispatchRequest

class OpenApiMessageValidator {
    fun validateOpenApiQuestion(questionDispatch: QuestionDispatchRequest): ValidationResult {
        return ValidationResult(true, mutableSetOf())
    }
}