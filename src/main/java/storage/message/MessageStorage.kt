package storage.message

import domain.Message

class MessageStorage : IMessageStorage {
    private val messages: MutableList<Message> = mutableListOf()

    override fun addUnauthorizedMessageToTopic(message: Message) {
    }

    override fun getMessagesToTopic(topic: String) {
        TODO("Not yet implemented")
    }

}