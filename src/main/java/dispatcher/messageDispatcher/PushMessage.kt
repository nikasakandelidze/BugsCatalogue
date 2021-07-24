package dispatcher.messageDispatcher

data class PushMessage(val message: String, val to: String, val from: String, val description: String)