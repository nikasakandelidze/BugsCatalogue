package storage.message

import domain.Message

interface IMessageStorage {
    fun addUnauthorizedMessageToTopic(message: Message)
    fun getMessagesToTopic(topic: String)
}