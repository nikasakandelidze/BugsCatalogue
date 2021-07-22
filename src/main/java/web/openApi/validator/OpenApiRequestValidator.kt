package web.openApi.validator

import common.validator.ValidationResult
import web.dto.MessageRequest

class OpenApiRequestValidator {
    fun validateBugsListRequest(messageRequest: MessageRequest): ValidationResult {
        if (messageRequest.content.isNullOrEmpty() || messageRequest.title.isNullOrEmpty() || messageRequest.topicId.isNullOrEmpty() || messageRequest.email.isNullOrEmpty()) {
            return ValidationResult(
                false,
                mutableSetOf("All fields: [content, title, topicId, email] must be specified.")
            )
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }
}