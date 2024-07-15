package backend.itracker.schedule.service.notification

import backend.itracker.schedule.service.notification.handler.NotificationComposite
import backend.itracker.tracker.favorite.service.FavoriteService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private const val CRAWLING_TIME = "0 0 5 * * *"
private const val TIME_ZONE = "Asia/Seoul"

@Component
class NotificationSchedulerService(
    private val favoriteService: FavoriteService,
    private val notificationComposite: NotificationComposite,
    private val notificationSender: NotificationSender,
) {

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun scheduleNotification() {
        val productCategoryMap = favoriteService.findDecreasedPriceAll()
            .groupBy { it.product}
            .mapValues { entry -> entry.value.map { it.member } }

        var reservationCount = 0
        productCategoryMap.forEach { (product, members) ->
            val priceChangeNotificationInfo = notificationComposite.getPriceChangeNotificationInfo(product)
            val receiverPhoneNumbers = members.mapNotNull { it.phoneNumber }
            reservationCount += notificationSender.reserveNotificationOfPriceChange(priceChangeNotificationInfo, receiverPhoneNumbers)
        }

        if (reservationCount > 0) {
            notificationSender.checkBalance(reservationCount)
        }
    }
}
