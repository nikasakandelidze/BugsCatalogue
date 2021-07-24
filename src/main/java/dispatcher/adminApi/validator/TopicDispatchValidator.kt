package dispatcher.adminApi.validator

import common.validator.ValidationResult
import domain.Topic

class TopicDispatchValidator {
    fun validateNewTopic(topic: Topic): ValidationResult {
        if (topic == null || topic.title.isNullOrEmpty() || topic.description.isNullOrEmpty()) {
            return ValidationResult(false, mutableSetOf("new topic's title and description must be present"))
        } else {
            return ValidationResult(true, mutableSetOf())
        }
    }

}