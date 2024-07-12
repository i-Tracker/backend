package backend.itracker.alarm.service.event

interface FailedNotificationEvent {

    fun getMessages(): List<String>

    fun getCause(): String?
}
