package backend.itracker.schedule.infra.notification.solapi

import backend.itracker.schedule.infra.notification.event.MessageReservationSuccessEvent
import backend.itracker.schedule.infra.notification.event.MessageSendFailEvent
import backend.itracker.schedule.infra.notification.solapi.config.NurigoKakaoChannelConfig
import backend.itracker.schedule.service.notification.NotificationSender
import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException
import net.nurigo.sdk.message.model.KakaoOption
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

val logger = KotlinLogging.logger {}

private const val RESERVE_FAIlED = 0

@Profile("!test")
@Component
class NotificationClient(
    private val messageService: DefaultMessageService,
    private val nurigoKakaoChannelConfig: NurigoKakaoChannelConfig,
    private val eventPublisher: ApplicationEventPublisher,
) : NotificationSender {

    override fun reserveNotificationOfPriceChange(
        priceChangeTemplate: PriceChangeNotificationInfo,
        receiverPhoneNumbers: List<String>,
    ): Int {
        val option = KakaoOption(
            pfId = nurigoKakaoChannelConfig.pfId,
            templateId = nurigoKakaoChannelConfig.priceChangeNotificationTemplateId,
            disableSms = true,
            variables = priceChangeTemplate.mutableMap()
        )

        val messages = receiverPhoneNumbers.map {
            Message(
                to = it,
                from = nurigoKakaoChannelConfig.primaryPhoneNumber,
                kakaoOptions = option
            )
        }

        val scheduledDateTime = getReservationTime().toInstant(ZoneOffset.of("+9"))!!

        try {
            messageService.send(messages, scheduledDateTime)
            return messages.size
        } catch (exception: NurigoMessageNotReceivedException) {
            logger.error {
                """
                [ 메세지 전송 실패 ]
                실패 리스트 : ${exception.failedMessageList}
                실패 메세지 : ${exception.message}
                """.trimIndent()
            }

            eventPublisher.publishEvent(
                MessageSendFailEvent.of(
                    exception.failedMessageList,
                    exception.message
                )
            )

            return RESERVE_FAIlED
        }
    }

    override fun checkBalance(reservationCount: Int) {
        try {
            val balance = messageService.getBalance()
            eventPublisher.publishEvent(
                MessageReservationSuccessEvent(
                    reservationTime = getReservationTime(),
                    point = balance.point!!,
                    balance = balance.balance!!,
                    successMessageCount = reservationCount
                )
            )
        } catch (exception: NurigoMessageNotReceivedException) {
            logger.error { "잔액 확인 실패 cause : $exception" }
        }
    }

    private fun getReservationTime(): LocalDateTime {
        return LocalDateTime.of(
            LocalDate.now(), LocalTime.of(
                nurigoKakaoChannelConfig.sendingHour,
                0
            )
        )
    }
}
