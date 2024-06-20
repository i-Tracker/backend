package backend.itracker.tracker.product.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.filter.MacbookFilterResponse
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.response.product.macbook.MacbookDetailResponse
import backend.itracker.tracker.product.response.product.macbook.MacbookResponse
import backend.itracker.tracker.product.vo.ProductFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class MacbookHandler(
    private val macbookService: MacbookService,
) : ProductHandleable {

    override fun supports(productCategory: ProductCategory): Boolean {
        return productCategory == ProductCategory.MACBOOK_AIR ||
                productCategory == ProductCategory.MACBOOK_PRO
    }

    override fun findTopDiscountPercentageProducts(
        productCategory: ProductCategory,
        limit: Int
    ): List<CommonProductModel> {
        val macbooks = macbookService.findAllFetchByCategory(productCategory.toMacbookCategory())
        return macbooks.map { MacbookResponse.from(it) }
            .sortedBy { it.discountPercentage }
            .take(limit)
    }

    override fun findFilter(
        category: ProductCategory,
        filter: ProductFilter
    ): CommonFilterModel {
        val macbooks = macbookService.findAllByCategoryAndFilter(category.toMacbookCategory(), MacbookFilterCondition(filter.value))

        return MacbookFilterResponse.from(macbooks)
    }

    override fun findFilteredProductsOrderByDiscountRate(
        category: ProductCategory,
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

        return macbooks.map { MacbookResponse.from(it) }
            .sortedBy { it.discountPercentage }
            .slice(startElementNumber until lastElementNumber)
    }

    override fun findProductById(productId: Long): CommonProductDetailModel {
        val macbook = macbookService.findMacbookById(productId)

        return MacbookDetailResponse.from(macbook)
    }
}
