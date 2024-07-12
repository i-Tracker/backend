package backend.itracker.schedule.infra.notification.event

import backend.itracker.alarm.service.event.FailedNotificationEvent
import net.nurigo.sdk.message.model.FailedMessage

data class MessageSendFailEvent(
    val failedMessages: List<FailedMessageInfo>,
    val causeMessage: String?,
) : FailedNotificationEvent {

    companion object {
        fun of(failedMessageList: List<FailedMessage>, causeMessage: String?): MessageSendFailEvent {
            return MessageSendFailEvent(
                failedMessages = failedMessageList.map { FailedMessageInfo.from(it) },
                causeMessage = causeMessage
            )
        }
    }

    override fun getMessages(): List<String> {
        return failedMessages.map {
            """
                *to*: ${it.to}
                *from*: ${it.from}
                *type*: ${it.type}
                *country*: ${it.country}
                *messageId*: ${it.messageId}
                *statusCode*: ${it.statusCode}
                *statusMessage*: ${it.statusMessage}
                *accountId*: ${it.accountId}
            """.trimIndent()
        }
    }

    override fun getCause(): String? {
        return causeMessage
    }
}

data class FailedMessageInfo(
    var to: String? = null,
    var from: String? = null,
    var type: String? = null,
    var country: String? = null,
    var messageId: String? = null,
    var statusCode: String? = null,
    var statusMessage: String? = null,
    var accountId: String? = null
) {

    companion object {
        fun from(message: FailedMessage): FailedMessageInfo {
            return FailedMessageInfo(
                to = message.to,
                from = message.from,
                type = message.type,
                country = message.country,
                messageId = message.messageId,
                statusCode = message.statusCode,
                statusMessage = message.statusMessage,
                accountId = message.accountId,
            )
        }
    }
}
