package backend.itracker.tracker.service.response.product.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.tracker.service.response.product.CommonPriceInfo
import backend.itracker.tracker.service.response.product.CommonProductDetailModel
import java.math.BigDecimal

class MacbookDetailResponse(
    val id: Long,
    val title: String,
    val category: String,
    val size: Int,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val color: String,
    val label: Boolean,
    val imageUrl: String,
    val coupangUrl: String,
    val isOutOfStock: Boolean,

    discountPercentage: Int,
    currentPrice: BigDecimal,
    allTimeHighPrice: BigDecimal,
    allTimeLowPrice: BigDecimal,
    averagePrice: BigDecimal,
    priceInfos: List<CommonPriceInfo>,
) : CommonProductDetailModel(
    discountPercentage,
    currentPrice,
    allTimeHighPrice,
    allTimeLowPrice,
    averagePrice,
    priceInfos
) {

    companion object {
        fun from(macbook: Macbook): MacbookDetailResponse {
            val koreanCategory = when (macbook.category) {
                MacbookCategory.MACBOOK_AIR -> "맥북 에어"
                MacbookCategory.MACBOOK_PRO -> "맥북 프로"
            }

            return MacbookDetailResponse(
                id = macbook.id,
                title = "${macbook.company} ${macbook.releaseYear} $koreanCategory ${macbook.size}",
                category = macbook.category.name.lowercase(),
                size = macbook.size,
                discountPercentage = macbook.findDiscountPercentage(),
                currentPrice = macbook.findCurrentPrice(),
                allTimeHighPrice = macbook.findAllTimeHighPrice(),
                allTimeLowPrice = macbook.findAllTimeLowPrice(),
                averagePrice = macbook.findAveragePrice(),
                chip = macbook.chip,
                cpu = "${macbook.cpu} CPU",
                gpu = "${macbook.gpu} GPU",
                storage = "${macbook.storage} SSD 저장 장치",
                memory = "${macbook.memory} 통합 메모리",
                color = macbook.color,
                label = macbook.isAllTimeLowPrice(),
                imageUrl = macbook.thumbnail,
                coupangUrl = macbook.coupangLink.ifBlank { macbook.productLink },
                isOutOfStock = macbook.isOutOfStock(),
                priceInfos = macbook.getRecentPricesByPeriod(SIX_MONTH).macbookPrices
                    .map { CommonPriceInfo.of(it.createdAt, it.currentPrice) }
            )
        }
    }
}
