package backend.itracker.tracker.product.controller

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.common.response.Pages
import backend.itracker.tracker.product.handler.ProductHandlerImpl
import backend.itracker.tracker.product.response.product.CommonProductModel
import backend.itracker.tracker.product.vo.Limit
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    private val productHandler: ProductHandlerImpl,
) {

    @GetMapping("/api/v1/products")
    fun getAllProductsByFilter(
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<Pages<CommonProductModel>> {
        val macbookAirs =
            productHandler.findTopDiscountPercentageProducts(ProductCategory.MACBOOK_AIR, Limit(limit))
        val macbookPros =
            productHandler.findTopDiscountPercentageProducts(ProductCategory.MACBOOK_PRO, Limit(limit))
        val airPods =
            productHandler.findTopDiscountPercentageProducts(ProductCategory.AIRPODS, Limit(limit))

        val allProducts = macbookAirs + macbookPros + airPods
        return ResponseEntity.ok(Pages.withPagination(allProducts.sortedBy { it.discountPercentage() }
            .take(limit)))
    }
}
