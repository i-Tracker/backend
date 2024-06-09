package backend.itracker.tracker.service.response.product.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.tracker.service.response.product.CommonProductModel
import java.math.BigDecimal

data class MacbookResponse(
    val id: Long,
    val title: String,
    val category: String,
    val size: Int,
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val chip: String,
    val cpu: String,
    val gpu: String,
    val storage: String,
    val memory: String,
    val color: String,
    val label: Boolean,
    val imageUrl: String,
    val isOutOfStock: Boolean
) : CommonProductModel {

    companion object {
        fun from(macbook: Macbook): MacbookResponse {
            val koreanCategory = when (macbook.category) {
                MacbookCategory.MACBOOK_AIR -> "맥북 에어"
                MacbookCategory.MACBOOK_PRO -> "맥북 프로"
            }

            return MacbookResponse(
                id = macbook.id,
                title = "${macbook.company} ${macbook.releaseYear} $koreanCategory ${macbook.size}",
                category = macbook.category.name.lowercase(),
                size = macbook.size,
                discountPercentage = macbook.findDiscountPercentage(),
                chip = macbook.chip,
                cpu = "${macbook.cpu} CPU",
                gpu = "${macbook.gpu} GPU",
                storage = "${macbook.storage} SSD 저장 장치",
                memory = "${macbook.memory} 통합 메모리",
                color = macbook.color,
                currentPrice = macbook.findCurrentPrice(),
                label = macbook.isAllTimeLowPrice(),
                imageUrl = macbook.thumbnail,
                isOutOfStock = macbook.isOutOfStock()
            )
        }
    }
}

