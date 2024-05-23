package backend.itracker.tracker.service

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.response.CommonProductModel
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productHandlers: List<ProductHandler>
) {

    fun findTopDiscountPercentageProducts(
        productCategory: ProductCategory,
        limit: Int
    ): List<CommonProductModel> {
        val productHandler = (productHandlers.find { it.supports(productCategory) }
            ?: throw IllegalArgumentException("지원하지 않는 카테고리 입니다. category: $productCategory"))

        return productHandler.findTopDiscountPercentageProducts(productCategory, limit)
    }
}
