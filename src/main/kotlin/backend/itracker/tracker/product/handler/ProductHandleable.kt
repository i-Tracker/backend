package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.vo.ProductFilter
import backend.itracker.tracker.product.vo.ProductInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProductHandleable {

    fun supports(productFilterCategory: ProductFilterCategory): Boolean

    /**
     * @param productFilterCategory 상품 카테고리  -> 맥북에서만 사용
     */
    fun findTopDiscountPercentageProducts(productFilterCategory: ProductFilterCategory, limit: Int): List<CommonProductModel>

    /**
     * @param category 상품 카테고리  -> 맥북에서만 사용
     */
    fun findFilter(category: ProductFilterCategory, filter: ProductFilter): CommonFilterModel

    /**
     * @param category 상품 카테고리  -> 맥북에서만 사용
     */
    fun findFilteredProductsOrderByDiscountRate(
        category: ProductFilterCategory,
        filter: ProductFilter,
        pageable: Pageable,
    ): Page<CommonProductModel>

    fun findProductById(productInfo: ProductInfo, member: Member): CommonProductDetailModel
}
