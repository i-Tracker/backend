package backend.itracker.alarm.service.event

interface SuccessNotificationEvent {

    fun getSuccessMessageCount(): Int

    fun getPoint(): Float

    fun getBalance(): Float
}
