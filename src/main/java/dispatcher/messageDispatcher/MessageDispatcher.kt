package dispatcher.messageDispatcher

import io.vertx.core.Vertx
import io.vertx.ext.mail.MailClient
import io.vertx.ext.mail.MailConfig
import io.vertx.ext.mail.MailMessage
import io.vertx.ext.mail.StartTLSOptions

class MessageDispatcher(vertx: Vertx) : IMessageDispatcher {
    private val mailClient: MailClient

    init {
        val config = MailConfig()
        config.setHostname("smtp.gmail.com")
        config.setPort(587)
        config.setStarttls(StartTLSOptions.REQUIRED)
        //configure username and password of SMPT client for your own uses
//        config.setUsername("")
//        config.setPassword("")
        mailClient = MailClient.create(vertx, config)
    }

    override fun sendMessage(pushMessage: PushMessage, callback: (PushMessage) -> Unit) {
        val mailMessage = MailMessage()
        mailMessage.setFrom(pushMessage.from)
        mailMessage.setTo(pushMessage.to)
        mailMessage.setHtml(pushMessage.message)
        mailClient.sendMail(mailMessage) {
            if (it.succeeded()) {
                callback(pushMessage)
            } else {
                print(it.cause())
            }
        }
    }
}