package common.validator

data class ValidationResult(val isValid: Boolean, val messages: MutableSet<String>)