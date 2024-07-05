package backend.itracker.tracker.member.service.vo

import backend.itracker.crawl.common.ProductCategory

data class FavoriteCount(
    val productId: Long,
    val category: ProductCategory,
    val count: Long
)
