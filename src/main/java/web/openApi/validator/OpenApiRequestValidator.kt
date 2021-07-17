package web.openApi.validator

import common.validator.ValidationResult
import web.dto.BugsFilterRequest

class OpenApiRequestValidator {
    fun validateBugsListRequest(bugsFilterRequest: BugsFilterRequest): ValidationResult {
        if (bugsFilterRequest.content == null && bugsFilterRequest.title == null && bugsFilterRequest.userId == null && bugsFilterRequest.id == null) {
            return ValidationResult(false, mutableSetOf("All fields: [content, title, userId, id] can't be null"))
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }
}