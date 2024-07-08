package backend.itracker.schedule.service.notification.handler

import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import org.springframework.stereotype.Component

@Component
class NotificationComposite(
    private val handlers: Set<NotificationHandleable>,
) {
    private val notificationHandlers = handlers.associateBy { it.supports() }

    fun getPriceChangeNotificationInfo(product: FavoriteProduct): PriceChangeNotificationInfo {
        return notificationHandlers[product.productCategory]?.getPriceChangeNotificationInfo(product)
            ?: throw IllegalArgumentException("지원하지 않는 알림 카테고리 타입입니다. category: ${product.productCategory}")
    }
}
