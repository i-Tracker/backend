package backend.itracker.tracker.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.filter.MacbookFilterResponse
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.response.product.MacbookResponse
import backend.itracker.tracker.service.vo.ProductFilter
import org.springframework.stereotype.Component


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
            val koreanCategory = when (it.category) {
                ProductCategory.MACBOOK_AIR -> "맥북 에어"
                ProductCategory.MACBOOK_PRO -> "맥북 프로"
                else -> ""
            }
            MacbookResponse(
                id = it.id,
                title = "${it.company} ${it.releaseYear} $koreanCategory ${it.size}",
                category = it.category.name.lowercase(),
                size = it.size,
                discountPercentage = it.findDiscountPercentage(),
                chip = it.chip,
                cpu = "${it.cpu} CPU",
                gpu = "${it.gpu} GPU",
                storage = "${it.storage} SSD 저장 장치",
                memory = "${it.memory} 통합 메모리",
                color = it.color,
                currentPrice = it.findCurrentPrice(),
                label = "역대최저가",
                imageUrl = it.thumbnail,
                isOutOfStock = it.isOutOfStock()
            )
        }.sortedBy { it.discountPercentage }
            .take(limit)
    }

    override fun findFilter(
        productCategory: ProductCategory,
        filter: ProductFilter
    ): CommonFilterModel {
        val macbooks = macbookService.findAllByProductCategoryAndFilter(productCategory, MacbookFilterCondition(filter.value))

        return MacbookFilterResponse.from(macbooks)
    }
}
