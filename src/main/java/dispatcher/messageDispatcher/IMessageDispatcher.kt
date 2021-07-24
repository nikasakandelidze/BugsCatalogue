package dispatcher.messageDispatcher

interface IMessageDispatcher {
    fun sendMessage(pushMessage: PushMessage, callback: (PushMessage) -> Unit)
}