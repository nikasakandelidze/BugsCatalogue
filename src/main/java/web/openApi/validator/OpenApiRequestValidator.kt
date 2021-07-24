package web.openApi.validator

import common.validator.ValidationResult
import web.dto.QuestionDispatchRequest

class OpenApiRequestValidator {
    fun validateDistpachNewQuestionRequest(questionDispatchRequest: QuestionDispatchRequest): ValidationResult {
        if (questionDispatchRequest.content.isNullOrEmpty() || questionDispatchRequest.title.isNullOrEmpty() || questionDispatchRequest.email.isNullOrEmpty()) {
            return ValidationResult(
                false,
                mutableSetOf("All fields: [content, title, topicId, email] must be specified.")
            )
        } else {
            if (questionDispatchRequest.topicId != -1) {
                return ValidationResult(true, mutableSetOf())
            } else {
                return ValidationResult(false, mutableSetOf("TopicId must be numeric"))
            }
        }
    }
}