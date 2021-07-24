package web.adminApi.validator

import common.validator.ValidationResult
import domain.Topic

class AdminInputValidator {
    fun validateNewTopic(topic: Topic?): ValidationResult {
        if (topic == null || topic.title.isNullOrEmpty() || topic.description.isNullOrEmpty()) {
            return ValidationResult(false, mutableSetOf("topic's title and description must be presenst"))
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }
}