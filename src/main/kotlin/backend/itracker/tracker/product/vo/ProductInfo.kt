package backend.itracker.tracker.product.vo

import backend.itracker.crawl.common.ProductFilterCategory

data class ProductInfo(
    val productFilterCategory: ProductFilterCategory,
    val productId: Long,
)
