package backend.itracker.tracker.controller

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.controller.response.CategoryResponses
import backend.itracker.tracker.controller.response.Pages
import backend.itracker.tracker.service.ProductService
import backend.itracker.tracker.service.response.CommonProductModel
import backend.itracker.tracker.service.vo.Limit
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
}
