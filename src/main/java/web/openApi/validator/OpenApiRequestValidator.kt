package web.openApi.validator

import common.validator.ValidationResult
import web.dto.SendMessageRequest

class OpenApiRequestValidator {
    fun validateBugsListRequest(sendMessageRequest: SendMessageRequest): ValidationResult {
        if (sendMessageRequest.content == null && sendMessageRequest.title == null && sendMessageRequest.topic == null) {
            return ValidationResult(false, mutableSetOf("All fields: [content, title, topic] can't be empty."))
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }
}