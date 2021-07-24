package domain

data class User(
    val id: Int = -1,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
)