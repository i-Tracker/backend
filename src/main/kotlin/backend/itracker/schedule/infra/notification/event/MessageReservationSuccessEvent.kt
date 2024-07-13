package backend.itracker.schedule.infra.notification.event

import backend.itracker.alarm.service.event.SuccessReservationOfNotificationEvent
import java.time.LocalDateTime

data class MessageReservationSuccessEvent(
    val reservationTime: LocalDateTime,
    val successMessageCount: Int,
    val point: Float,
    val balance: Float,
) : SuccessReservationOfNotificationEvent {

    override fun reservationTime(): LocalDateTime {
        return reservationTime
    }

    override fun successMessageCount(): Int {
        return successMessageCount
    }

    override fun point(): Float {
        return point
    }

    override fun balance(): Float {
        return balance
    }
}
