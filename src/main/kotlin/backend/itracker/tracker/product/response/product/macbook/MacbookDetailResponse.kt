package backend.itracker.tracker.product.response.product.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.tracker.product.response.product.CommonPriceInfo
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
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

    isFavorite: Boolean,
    discountPercentage: Int,
    currentPrice: BigDecimal,
    allTimeHighPrice: BigDecimal,
    allTimeLowPrice: BigDecimal,
    averagePrice: BigDecimal,
    priceInfos: List<CommonPriceInfo>,
) : CommonProductDetailModel(
    isFavorite,
    discountPercentage,
    currentPrice,
    allTimeHighPrice,
    allTimeLowPrice,
    averagePrice,
    priceInfos
) {

    companion object {
        fun of(macbook: Macbook, isFavorite: Boolean = false): MacbookDetailResponse {
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
                coupangUrl = macbook.partnersLink.ifBlank { macbook.productLink },
                isFavorite = isFavorite,
                isOutOfStock = macbook.isOutOfStock(),
                priceInfos = macbook.getRecentPricesByPeriod(SIX_MONTH).macbookPrices
                    .map { CommonPriceInfo.of(it.createdAt, it.currentPrice) }
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MacbookDetailResponse

        if (id != other.id) return false
        if (title != other.title) return false
        if (category != other.category) return false
        if (size != other.size) return false
        if (chip != other.chip) return false
        if (cpu != other.cpu) return false
        if (gpu != other.gpu) return false
        if (storage != other.storage) return false
        if (memory != other.memory) return false
        if (color != other.color) return false
        if (label != other.label) return false
        if (imageUrl != other.imageUrl) return false
        if (coupangUrl != other.coupangUrl) return false
        if (isOutOfStock != other.isOutOfStock) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + size
        result = 31 * result + chip.hashCode()
        result = 31 * result + cpu.hashCode()
        result = 31 * result + gpu.hashCode()
        result = 31 * result + storage.hashCode()
        result = 31 * result + memory.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + coupangUrl.hashCode()
        result = 31 * result + isOutOfStock.hashCode()
        return result
    }
}
