package domain

class Topic(
    val id: String? = "",
    val title: String? = "",
    val description: String? = "",
    val questions: MutableList<Question>? = mutableListOf()
)