package backend.itracker.tracker.controller

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.controller.response.CategoryResponses
import backend.itracker.tracker.controller.response.Pages
import backend.itracker.tracker.controller.response.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

private val productCategories = ProductCategory.entries

@RestController
class ProductController(
    private val macbookService: MacbookService
) {

    @GetMapping("/api/v1/products/{category}")
    fun findProductsByCategory(@PathVariable category: ProductCategory): ResponseEntity<Pages<ProductResponse>> {

        if (
            category == ProductCategory.MACBOOK_AIR ||
            category == ProductCategory.MACBOOK_PRO
        ) {
            val macbooks = macbookService.findAllWithRecentPricesByProductGategory(category)
            return ResponseEntity.ok(
                Pages(data = macbooks.map {
                    ProductResponse(
                        id = it.id,
                        title = it.name,
                        category = it.category.name.lowercase(),
                        inch = it.size,
                        discountPercentage = it.prices.last().discountPercentage,
                        basePrice = it.prices.last().basePrice,
                        currentPrice = it.prices.last().currentPrice,
                        label = "역대최저가",
                        imageUrl = it.thumbnail,
                        isOutOfStock = it.isOutOfStock
                    )
                })
            )
        }

        return ResponseEntity.notFound().build()
    }

    @GetMapping("/api/v1/category")
    fun findAllCategories(): ResponseEntity<CategoryResponses> {
        return ResponseEntity.ok(CategoryResponses(productCategories))
    }
}
