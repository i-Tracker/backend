package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import backend.itracker.tracker.member.domain.FavoriteProduct
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.domain.repository.FavoriteRepository
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.filter.MacbookFilterResponse
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.response.product.macbook.MacbookDetailResponse
import backend.itracker.tracker.product.response.product.macbook.MacbookResponse
import backend.itracker.tracker.product.vo.ProductFilter
import backend.itracker.tracker.product.vo.ProductInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class MacbookHandler(
    private val macbookService: MacbookService,
    private val favoriteRepository: FavoriteRepository,
) : ProductHandleable {

    override fun supports(productFilterCategory: ProductFilterCategory): Boolean {
        return productFilterCategory == ProductFilterCategory.MACBOOK_AIR ||
                productFilterCategory == ProductFilterCategory.MACBOOK_PRO
    }

    override fun findTopDiscountPercentageProducts(
        productFilterCategory: ProductFilterCategory,
        limit: Int
    ): List<CommonProductModel> {
        val macbooks = macbookService.findAllFetchByCategory(productFilterCategory.toMacbookCategory())
        return macbooks.map {
            MacbookResponse.of(
                it,
                favoriteRepository.findCountByProduct(FavoriteProduct(it.id, ProductCategory.MACBOOK))
            )
        }
            .sortedBy { it.discountPercentage }
            .take(limit)
    }

    override fun findFilter(
        category: ProductFilterCategory,
        filter: ProductFilter
    ): CommonFilterModel {
        val macbooks = macbookService.findAllByCategoryAndFilter(category.toMacbookCategory(), MacbookFilterCondition(filter.value))

        return MacbookFilterResponse.from(macbooks)
    }

    override fun findFilteredProductsOrderByDiscountRate(
        category: ProductFilterCategory,
        filter: ProductFilter,
        pageable: Pageable,
    ): Page<CommonProductModel> {
        val macbooks = macbookService.findAllFetchByCategoryAndFilter(
            category.toMacbookCategory(),
            MacbookFilterCondition(filter.value),
        )

        return PageImpl(paginate(macbooks, pageable), pageable, macbooks.size.toLong())
    }

    private fun paginate(macbooks: List<Macbook>, pageable: Pageable): List<MacbookResponse> {
        val startElementNumber = pageable.offset.toInt()
        val lastElementNumber = min(startElementNumber + pageable.pageSize, macbooks.size)
        if (startElementNumber >= macbooks.size) {
            return emptyList()
        }

        return macbooks.map {
            MacbookResponse.of(
                it,
                favoriteRepository.findCountByProduct(FavoriteProduct(it.id, ProductCategory.MACBOOK))
            )
        }
            .sortedBy { it.discountPercentage }
            .slice(startElementNumber until lastElementNumber)
    }

    override fun findProductById(productInfo: ProductInfo, member: Member): CommonProductDetailModel {
        val macbook = macbookService.findMacbookById(productInfo.productId)

        val isFavorite = favoriteRepository.findByFavorite(
            member.id,
            FavoriteProduct(productInfo.productId, productInfo.productCategory)
        ).isPresent
        val notificationCount = favoriteRepository.findCountByProduct(
            FavoriteProduct(productInfo.productId, productInfo.productCategory)
        )

        return MacbookDetailResponse.of(macbook, notificationCount, isFavorite)
    }
}
