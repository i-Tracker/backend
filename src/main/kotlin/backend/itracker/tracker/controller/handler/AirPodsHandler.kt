package backend.itracker.tracker.controller.handler

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.product.CommonProductDetailModel
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.response.product.airpods.AirPodsDetailResponse
import backend.itracker.tracker.service.response.product.airpods.AirPodsResponse
import backend.itracker.tracker.service.vo.ProductFilter
import jakarta.transaction.NotSupportedException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class AirPodsHandler(
    private val airPodsService: AirPodsService
) : ProductHandleable {
    override fun supports(productCategory: ProductCategory): Boolean {
        return ProductCategory.AIRPODS == productCategory
    }

    override fun findTopDiscountPercentageProducts(
        productCategory: ProductCategory,
        limit: Int
    ): List<CommonProductModel> {
        throw NotSupportedException("AirPodsHandler는 최저가 순위를 지원하지 않습니다.")
    }

    override fun findFilter(productCategory: ProductCategory, filterCondition: ProductFilter): CommonFilterModel {
        throw NotSupportedException("AirPodsHandler는 필터링를 지원하지 않습니다.")
    }

    /**
     * @param category 상품 카테고리  -> 맥북에서만 사용
     * @param filter 필터 조건 -> 에어팟은 상품 수가 적어서 사용 안 함
     * @return 현재 전체 에어팟을 반환 중
     */
    override fun findFilteredProductsOrderByDiscountRate(
        category: ProductCategory,
        filter: ProductFilter,
        pageable: Pageable
    ): Page<CommonProductModel> {
        val airpods = airPodsService.findAllFetch()
        val contents = airpods.map { AirPodsResponse.from(it) }
            .sortedBy { it.discountPercentage }

        return PageImpl(contents, PageRequest.of(0, airpods.size), airpods.size.toLong())
    }

    override fun findProductById(productId: Long): CommonProductDetailModel {
        val airPods = airPodsService.findByIdAllFetch(productId)

        return AirPodsDetailResponse.from(airPods)
    }
}
