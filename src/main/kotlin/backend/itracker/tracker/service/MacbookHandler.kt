package backend.itracker.tracker.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.controller.response.MacbookResponse
import backend.itracker.tracker.service.response.CommonProductModel
import org.springframework.stereotype.Component
import java.math.BigDecimal


@Component
class MacbookHandler(
    private val macbookService: MacbookService
) : ProductHandler {

    override fun supports(productCategory: ProductCategory): Boolean {
        return productCategory == ProductCategory.MACBOOK_AIR ||
                productCategory == ProductCategory.MACBOOK_PRO
    }

    override fun findTopDiscountPercentageProducts(
        productCategory: ProductCategory,
        limit: Int
    ): List<CommonProductModel> {
        val macbooks = macbookService.findAllFetchByProductCategory(productCategory)
        return macbooks.map {
            val averagePrice =
                it.prices.sumOf { it.currentPrice }.divide(BigDecimal.valueOf(it.prices.size.toLong()))
            it.keepOnlyRecentPrice()
            val currentPrice = it.prices.last().currentPrice
            val discountPercentage =
                ((currentPrice - averagePrice) / averagePrice).multiply(BigDecimal.valueOf(100)).toInt()
            MacbookResponse(
                id = it.id,
                title = it.name,
                category = it.category.name.lowercase(),
                size = it.size,
                discountPercentage = discountPercentage,
                chip = it.cpu,
                cpu = it.cpu,
                gpu = it.gpu,
                storage = it.storage,
                memory = it.memory,
                color = it.color,
                currentPrice = currentPrice,
                imageUrl = it.thumbnail,
                isOutOfStock = it.isOutOfStock
            )
        }.sortedBy { it.discountPercentage }
            .take(limit)
    }
}
