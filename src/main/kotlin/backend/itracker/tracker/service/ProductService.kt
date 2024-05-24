package backend.itracker.tracker.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.handler.ProductHandler
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.vo.Limit
import backend.itracker.tracker.service.vo.ProductFilter
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productHandlers: List<ProductHandler>
) {

    fun findTopDiscountPercentageProducts(
        productCategory: ProductCategory,
        limit: Limit
    ): List<CommonProductModel> {
        val productHandler = (productHandlers.find { it.supports(productCategory) }
            ?: throw IllegalArgumentException("지원하지 않는 카테고리 입니다. category: $productCategory"))

        return productHandler.findTopDiscountPercentageProducts(productCategory, limit.value)
    }

    fun findFilter(
        productCategory: ProductCategory,
        productFilter: ProductFilter,
    ): CommonFilterModel {
        val productHandler = productHandlers.find { it.supports(productCategory) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: $productCategory")

        return productHandler.findFilter(productCategory, productFilter)
    }
}
