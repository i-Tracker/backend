package backend.itracker.tracker.controller.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.macbook.service.dto.MacbookFilterCondition
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.filter.MacbookFilterResponse
import backend.itracker.tracker.service.response.product.CommonProductDetailModel
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.response.product.macbook.MacbookDetailResponse
import backend.itracker.tracker.service.response.product.macbook.MacbookResponse
import backend.itracker.tracker.service.vo.ProductFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component


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
        val macbooks = macbookService.findAllFetchByProductCategory(productCategory)
        return macbooks.map { MacbookResponse.from(it) }
            .sortedBy { it.discountPercentage }
            .take(limit)
    }

    override fun findFilter(
        productCategory: ProductCategory,
        filter: ProductFilter
    ): CommonFilterModel {
        val macbooks = macbookService.findAllByProductCategoryAndFilter(productCategory, MacbookFilterCondition(filter.value))

        return MacbookFilterResponse.from(macbooks)
    }

    override fun findFilteredProductsOrderByDiscountRate(
        category: ProductCategory,
        filter: ProductFilter,
        pageable: Pageable,
    ): Page<CommonProductModel> {
        val pageMacbooks = macbookService.findAllProductsByFilter(
            category,
            MacbookFilterCondition(filter.value),
            pageable
        )

        val contents = pageMacbooks.content.map { MacbookResponse.from(it) }
            .sortedBy { it.discountPercentage }

        return PageImpl(contents, pageMacbooks.pageable, pageMacbooks.totalElements)
    }

    override fun findProductById(productId: Long): CommonProductDetailModel {
        val macbook = macbookService.findMacbookById(productId)

        return MacbookDetailResponse.from(macbook)
    }
}
