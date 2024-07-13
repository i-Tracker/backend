package backend.itracker.schedule.service.notification

import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo

interface NotificationSender {

    fun reserveNotificationOfPriceChange(
        priceChangeTemplate: PriceChangeNotificationInfo,
        receiverPhoneNumbers: List<String>,
    )
}
