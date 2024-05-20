package backend.itracker.tracker.controller

import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.service.Category
import backend.itracker.tracker.controller.response.Pages
import backend.itracker.tracker.controller.response.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val macbookService: MacbookService
) {

    @GetMapping("/products/{categoryId}")
    fun findProductsByCategory(@PathVariable categoryId: Long): ResponseEntity<Pages<ProductResponse>> {

        val category = (Category.entries.find { it.categoryId == categoryId }
            ?: return ResponseEntity.notFound().build())

        if (category == Category.MACBOOK) {
            val macbooks = macbookService.findAllWithRecentPrices()
            return ResponseEntity.ok(
                Pages(data = macbooks.map {
                    ProductResponse(
                        id = it.id,
                        title = it.name,
                        category = it.type,
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
}
