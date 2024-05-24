package backend.itracker.tracker.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.service.common.DataSizeComparator
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.filter.MacbookFilter
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.response.product.MacbookResponse
import org.springframework.stereotype.Component


private val dataSizeComparator: DataSizeComparator = DataSizeComparator()

@Component
class MacbookHandler(
    private val macbookService: MacbookService,
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

    override fun findFilter(
        productCategory: ProductCategory
    ): CommonFilterModel {
        val macbooks = macbookService.findAll()

        return MacbookFilter(
            size = macbooks.map { it.size }.distinct().sorted(),
            color = macbooks.map { it.color }.distinct().sorted(),
            processor = macbooks.map { it.chip }.distinct().sorted(),
            storage = macbooks.map { it.storage }.distinct().sortedWith(dataSizeComparator),
            memory = macbooks.map { it.memory }.distinct().sortedWith(dataSizeComparator)
        )
    }
}
