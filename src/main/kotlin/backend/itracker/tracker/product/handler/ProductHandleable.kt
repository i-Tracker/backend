package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.vo.ProductFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProductHandleable {

    fun supports(productCategory: ProductCategory): Boolean

    /**
     * @param productCategory 상품 카테고리  -> 맥북에서만 사용
     */
    fun findTopDiscountPercentageProducts(productCategory: ProductCategory, limit: Int): List<CommonProductModel>

    /**
     * @param productCategory 상품 카테고리  -> 맥북에서만 사용
     */
    fun findFilter(productCategory: ProductCategory, filterCondition: ProductFilter): CommonFilterModel

    /**
     * @param category 상품 카테고리  -> 맥북에서만 사용
     */
    fun findFilteredProductsOrderByDiscountRate(
        category: ProductCategory,
        filter: ProductFilter,
        pageable: Pageable,
    ): Page<CommonProductModel>

    fun findProductById(productId: Long): CommonProductDetailModel
}
