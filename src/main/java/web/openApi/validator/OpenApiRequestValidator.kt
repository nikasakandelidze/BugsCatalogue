package web.openApi.validator

import common.validator.ValidationResult
import web.dto.MessageRequest

class OpenApiRequestValidator {
    fun validateBugsListRequest(messageRequest: MessageRequest): ValidationResult {
        if (messageRequest.content == null || messageRequest.title == null || messageRequest.topicId == null || messageRequest.email == null) {
            return ValidationResult(
                false,
                mutableSetOf("All fields: [content, title, topicId, email] must be specified.")
            )
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }
}