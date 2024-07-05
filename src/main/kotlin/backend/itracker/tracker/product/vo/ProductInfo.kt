package backend.itracker.tracker.product.vo

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.common.ProductFilterCategory

data class ProductInfo(
    val productCategory: ProductCategory,
    val productFilterCategory: ProductFilterCategory,
    val productId: Long,
) {
    companion object {
        fun of(category: ProductFilterCategory, productId: Long): ProductInfo {
            return ProductInfo(
                productCategory = ProductCategory.from(category),
                productFilterCategory = category,
                productId = productId
            )
        }
    }
}
