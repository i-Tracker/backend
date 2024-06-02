package backend.itracker.tracker.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.product.CommonProductDetailModel
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.vo.ProductFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProductHandler {

    fun supports(productCategory: ProductCategory): Boolean

    fun findTopDiscountPercentageProducts(productCategory: ProductCategory, limit: Int): List<CommonProductModel>

    fun findFilter(productCategory: ProductCategory, filterCondition: ProductFilter): CommonFilterModel

    fun findFilteredProductsOrderByDiscountRate(
        category: ProductCategory,
        filter: ProductFilter,
        pageable: Pageable,
    ): Page<CommonProductModel>

    fun findProductById(productId: Long): CommonProductDetailModel
}
