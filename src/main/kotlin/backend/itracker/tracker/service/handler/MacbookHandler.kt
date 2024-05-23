package backend.itracker.tracker.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.controller.response.MacbookResponse
import backend.itracker.tracker.service.response.CommonProductModel
import org.springframework.stereotype.Component


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
            MacbookResponse(
                id = it.id,
                title = it.name,
                category = it.category.name.lowercase(),
                size = it.size,
                discountPercentage = it.findDiscountPercentage(),
                chip = it.chip,
                cpu = it.cpu,
                gpu = it.gpu,
                storage = it.storage,
                memory = it.memory,
                color = it.color,
                currentPrice = it.findCurrentPrice(),
                imageUrl = it.thumbnail,
                isOutOfStock = it.isOutOfStock
            )
        }.sortedBy { it.discountPercentage }
            .take(limit)
    }
}
