package backend.itracker.tracker.product.vo

import backend.itracker.crawl.common.ProductCategory

data class ProductInfo(
    val productCategory: ProductCategory,
    val productId: Long,
)
