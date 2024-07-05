package backend.itracker.tracker.member.service.vo

import backend.itracker.crawl.common.ProductFilterCategory

data class FavoriteCount(
    val productId: Long,
    val category: ProductFilterCategory,
    val count: Long
)
