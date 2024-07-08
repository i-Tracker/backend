package backend.itracker.schedule.service.notification.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo
import backend.itracker.tracker.favorite.domain.FavoriteProduct

interface NotificationHandleable {

    fun supports(): ProductCategory

    fun getPriceChangeNotificationInfo(product: FavoriteProduct): PriceChangeNotificationInfo
}
