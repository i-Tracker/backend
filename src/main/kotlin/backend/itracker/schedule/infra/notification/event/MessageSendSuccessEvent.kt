package backend.itracker.schedule.infra.notification.event

import backend.itracker.alarm.service.event.SuccessNotificationEvent

data class MessageSendSuccessEvent(
    val successMessageCount: Int,
    val point: Float,
    val balance: Float,
) : SuccessNotificationEvent {

    override fun getSuccessMessageCount(): Int {
        return successMessageCount
    }

    override fun getPoint(): Float {
        return point
    }

    override fun getBalance(): Float {
        return balance
    }
}
