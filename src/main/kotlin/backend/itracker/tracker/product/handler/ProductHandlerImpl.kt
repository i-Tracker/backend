package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductCategory
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

    fun findFilteredProducts(
        category: ProductCategory,
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
        val productHandler = productHandlers.find { it.supports(productInfo.productCategory) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: ${productInfo.productCategory}")

        return productHandler.findProductById(productInfo, anonymousMember)
    }
}
