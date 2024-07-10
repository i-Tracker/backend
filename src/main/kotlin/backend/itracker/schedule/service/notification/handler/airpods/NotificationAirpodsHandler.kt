package backend.itracker.schedule.service.notification.handler.airpods

import backend.itracker.crawl.airpods.domain.AirPodsCategory
import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo
import backend.itracker.schedule.service.notification.handler.NotificationHandleable
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import org.springframework.stereotype.Component

@Component
class NotificationAirpodsHandler(
    private val airPodsService: AirPodsService,
) : NotificationHandleable {

    override fun supports(): ProductCategory {
        return ProductCategory.AIRPODS
    }

    override fun getPriceChangeNotificationInfo(product: FavoriteProduct): PriceChangeNotificationInfo {
        val airPods = airPodsService.findByIdAllFetch(product.productId)
        val name = when (airPods.category) {
            AirPodsCategory.AIRPODS -> "에어팟"
            AirPodsCategory.AIRPODS_PRO -> "에어팟 프로"
            AirPodsCategory.AIRPODS_MAX -> "에어팟 맥스"
        }

        return PriceChangeNotificationInfo(
            name = name,
            discountRate = airPods.findDiscountPercentage(),
            productDetail = """
                ${airPods.company} ${airPods.releaseYear} $name ${airPods.generation}세대
            """.trimIndent(),
            priceDrop = airPods.findPriceDiffFromYesterday(),
            rateDrop = airPods.findDiscountRateDiffFromYesterday(),
            currentPrice = airPods.findCurrentPrice(),
            category = ProductCategory.AIRPODS.toString(),
            productId = airPods.id,
            productPartnersLink = airPods.partnersLink,
        )
    }
}
