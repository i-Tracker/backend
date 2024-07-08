package backend.itracker.schedule.service.notification.handler.macbook

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.schedule.service.notification.dto.PriceChangeNotificationInfo
import backend.itracker.schedule.service.notification.handler.NotificationHandleable
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import org.springframework.stereotype.Component

@Component
class NotificationMacbookHandler(
    private val macbookService: MacbookService,
) : NotificationHandleable {

    override fun supports(): ProductCategory {
        return ProductCategory.MACBOOK
    }

    override fun getPriceChangeNotificationInfo(product: FavoriteProduct): PriceChangeNotificationInfo {
        val macbook = macbookService.findMacbookById(product.productId)

        val koreanCategory = when (macbook.category) {
            MacbookCategory.MACBOOK_AIR -> "맥북 에어"
            MacbookCategory.MACBOOK_PRO -> "맥북 프로"
        }
        return PriceChangeNotificationInfo(
            name = koreanCategory,
            discountRate = macbook.findDiscountPercentage(),
            productDetail = """
                ${macbook.company} ${macbook.releaseYear} $koreanCategory ${macbook.size}
                
                ${macbook.chip},
                ${macbook.cpu} CPU,
                ${macbook.gpu} GPU,
                ${macbook.storage} SSD 저장 장치,
                ${macbook.memory} 통합 메모리,
                ${macbook.color}
            """.trimIndent(),
            priceDropFromYesterday = macbook.findPriceDiffFromYesterday(),
            discountRateDropFromYesterday = macbook.findDiscountRateDiffFromYesterday(),
            currentPrice = macbook.findCurrentPrice(),
            category = macbook.category.toString(),
            productId = macbook.id,
            productPartnersLink = macbook.partnersLink,
        )
    }
}
