package storage.user

import domain.User

interface IUserStorage {
    fun getAllUsersSubscribedToTopicWithIdOf(topicId: Int, callback: (List<User>) -> Unit)
}