package backend.itracker.tracker.product.handler

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.member.domain.FavoriteProduct
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.domain.repository.FavoriteRepository
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.response.product.airpods.AirPodsDetailResponse
import backend.itracker.tracker.product.response.product.airpods.AirPodsResponse
import backend.itracker.tracker.product.vo.ProductFilter
import backend.itracker.tracker.product.vo.ProductInfo
import jakarta.transaction.NotSupportedException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class AirPodsHandler(
    private val airPodsService: AirPodsService,
    private val favoriteRepository: FavoriteRepository,
) : ProductHandleable {
    override fun supports(productFilterCategory: ProductFilterCategory): Boolean {
        return ProductFilterCategory.AIRPODS == productFilterCategory
    }

    override fun findTopDiscountPercentageProducts(
        productFilterCategory: ProductFilterCategory,
        limit: Int
    ): List<CommonProductModel> {
        val airpods = airPodsService.findAllFetch()
        val contents = airpods.map { AirPodsResponse.from(it) }
            .sortedBy { it.discountPercentage }
            .take(limit)

        return contents
    }

    override fun findFilter(productFilterCategory: ProductFilterCategory, filterCondition: ProductFilter): CommonFilterModel {
        throw NotSupportedException("AirPodsHandler는 필터링를 지원하지 않습니다.")
    }

    /**
     * @param category 상품 카테고리  -> 맥북에서만 사용
     * @param filter 필터 조건 -> 에어팟은 상품 수가 적어서 사용 안 함
     * @return 현재 전체 에어팟을 반환 중
     */
    override fun findFilteredProductsOrderByDiscountRate(
        category: ProductFilterCategory,
        filter: ProductFilter,
        pageable: Pageable
    ): Page<CommonProductModel> {
        val airpods = airPodsService.findAllFetch()
        val contents = airpods.map { AirPodsResponse.from(it) }
            .sortedBy { it.discountPercentage }

        return PageImpl(contents, PageRequest.of(0, airpods.size), airpods.size.toLong())
    }

    override fun findProductById(productInfo: ProductInfo, member: Member): CommonProductDetailModel {
        val airPods = airPodsService.findByIdAllFetch(productInfo.productId)

        val isFavorite = favoriteRepository.findByFavorite(
            member.id,
            FavoriteProduct(productInfo.productId, productInfo.productFilterCategory)
        ).isPresent

        return AirPodsDetailResponse.of(airPods, isFavorite)
    }
}
