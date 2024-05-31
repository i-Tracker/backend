package backend.itracker.tracker.controller

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.controller.response.CategoryResponses
import backend.itracker.tracker.controller.response.PageInfo
import backend.itracker.tracker.controller.response.Pages
import backend.itracker.tracker.controller.response.SinglePage
import backend.itracker.tracker.service.ProductService
import backend.itracker.tracker.service.response.filter.CommonFilterModel
import backend.itracker.tracker.service.response.product.CommonProductModel
import backend.itracker.tracker.service.vo.Limit
import backend.itracker.tracker.service.vo.ProductFilter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val productCategories = ProductCategory.entries

@RestController
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/api/v1/products/{category}")
    fun findProductsByCategory(
        @PathVariable category: ProductCategory,
        @RequestParam(defaultValue = "5") limit: Int,
    ): ResponseEntity<Pages<CommonProductModel>> {
        val products = productService.findTopDiscountPercentageProducts(category, Limit(limit))
        return ResponseEntity.ok(Pages(data = products))
    }

    @GetMapping("/api/v1/category")
    fun findAllCategories(): ResponseEntity<CategoryResponses> {
        return ResponseEntity.ok(CategoryResponses(productCategories))
    }

    @GetMapping("/api/v1/products/{category}/filter")
    fun findProductFilter(
        @PathVariable category: ProductCategory,
        @RequestParam filterConditon: Map<String, String>
    ): ResponseEntity<SinglePage<CommonFilterModel>> {
        val filter = productService.findFilter(category, ProductFilter(filterConditon))
        return ResponseEntity.ok(SinglePage(filter))
    }

    @GetMapping("/api/v1/products/{category}/search")
    fun findFilterdMacbookAir(
        @PathVariable category: ProductCategory,
        @RequestParam filterCondition: Map<String, String>,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "8") limit: Int,
    ): ResponseEntity<Pages<CommonProductModel>> {
        val pageProducts =
            productService.findFilteredProducts(category, ProductFilter(filterCondition), page, limit)

        return ResponseEntity.ok(
            Pages(
                data = pageProducts.content,
                pageInfo = PageInfo(
                    currentPage = pageProducts.number,
                    lastPage = pageProducts.totalPages,
                    elementSize = pageProducts.numberOfElements
                )
            )
        )
    }
}
