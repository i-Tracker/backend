package backend.itracker.tracker.product.controller

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.common.request.PageParams
import backend.itracker.tracker.common.response.Pages
import backend.itracker.tracker.common.response.SingleData
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.product.handler.ProductHandlerImpl
import backend.itracker.tracker.product.response.CategoryResponses
import backend.itracker.tracker.product.response.filter.CommonFilterModel
import backend.itracker.tracker.product.response.product.CommonProductDetailModel
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.vo.Limit
import backend.itracker.tracker.product.vo.ProductFilter
import backend.itracker.tracker.product.vo.ProductInfo
import backend.itracker.tracker.resolver.AnonymousMember
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val productCategories = ProductFilterCategory.entries

@RestController
class ProductController(
    private val productHandler: ProductHandlerImpl,
) {

    @GetMapping("/api/v1/products/{category}")
    fun findTopDiscountPercentageProductsByCategory(
        @PathVariable category: ProductFilterCategory,
        @RequestParam(defaultValue = "5") limit: Int,
    ): ResponseEntity<Pages<CommonProductModel>> {
        val products = productHandler.findTopDiscountPercentageProducts(category, Limit(limit))
        return ResponseEntity.ok(Pages(data = products))
    }

    @GetMapping("/api/v1/category")
    fun findAllCategories(): ResponseEntity<CategoryResponses> {
        return ResponseEntity.ok(CategoryResponses(productCategories))
    }

    @GetMapping("/api/v1/products/{category}/filter")
    fun findProductFilter(
        @PathVariable category: ProductFilterCategory,
        @RequestParam filterCondition: Map<String, String>
    ): ResponseEntity<SingleData<CommonFilterModel>> {
        val filter = productHandler.findFilter(category, ProductFilter(filterCondition))
        return ResponseEntity.ok(SingleData(filter))
    }

    @GetMapping("/api/v1/products/{category}/search")
    fun findFilterdProducts(
        @PathVariable category: ProductFilterCategory,
        @RequestParam filterCondition: Map<String, String>,
        @ModelAttribute pageParams: PageParams,
    ): ResponseEntity<Pages<CommonProductModel>> {
        val pageProducts =
            productHandler.findFilteredProducts(
                category,
                ProductFilter(filterCondition),
                PageRequest.of(pageParams.offset, pageParams.limit)
            )

        return ResponseEntity.ok(Pages.withPagination(pageProducts))
    }

    @GetMapping("/api/v1/products/{category}/{productId}")
    fun findFilteredProductDetail(
        @AnonymousMember member: Member,
        @PathVariable category: ProductFilterCategory,
        @PathVariable productId: Long,
    ): ResponseEntity<CommonProductDetailModel> {
        val product = productHandler.findProductById(ProductInfo.of(category, productId), member)

        return ResponseEntity.ok(product)
    }
}
