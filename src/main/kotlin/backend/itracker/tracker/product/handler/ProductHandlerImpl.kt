package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.vo.Limit
import backend.itracker.tracker.product.vo.ProductFilter
import backend.itracker.tracker.product.vo.ProductInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ProductHandlerImpl(
    private val productHandlers: List<ProductHandleable>
) {

    fun findTopDiscountPercentageProducts(
        productFilterCategory: ProductFilterCategory,
        limit: Limit
    ): List<CommonProductModel> {
        val productHandler = (productHandlers.find { it.supports(productFilterCategory) }
            ?: throw IllegalArgumentException("지원하지 않는 카테고리 입니다. category: $productFilterCategory"))

        return productHandler.findTopDiscountPercentageProducts(productFilterCategory, limit.value)
    }

    fun findFilter(
        productFilterCategory: ProductFilterCategory,
        productFilter: ProductFilter,
    ): CommonFilterModel {
        val productHandler = productHandlers.find { it.supports(productFilterCategory) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: $productFilterCategory")

        return productHandler.findFilter(productFilterCategory, productFilter)
    }

    fun findFilteredProducts(
        category: ProductFilterCategory,
        productFilter: ProductFilter,
        pageable: PageRequest
    ): Page<CommonProductModel> {
        val productHandler = productHandlers.find { it.supports(category) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: $category")

        return productHandler.findFilteredProductsOrderByDiscountRate(category, productFilter, pageable)
    }

    fun findProductById(
        productInfo: ProductInfo,
        anonymousMember: Member
    ): CommonProductDetailModel {
        val productHandler = productHandlers.find { it.supports(productInfo.productFilterCategory) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: ${productInfo.productFilterCategory}")

        return productHandler.findProductById(productInfo, anonymousMember)
    }
}
