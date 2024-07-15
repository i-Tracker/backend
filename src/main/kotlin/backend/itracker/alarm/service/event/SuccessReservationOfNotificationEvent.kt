package backend.itracker.alarm.service.event

import java.time.LocalDateTime

interface SuccessReservationOfNotificationEvent {

    fun reservationTime(): LocalDateTime

    fun successMessageCount(): Int

    fun point(): Float

    fun balance(): Float

    fun cost(): Long
}
