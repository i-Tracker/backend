package backend.itracker.tracker.member.service.vo

import backend.itracker.crawl.common.ProductFilterCategory

data class FavoriteInfo(
    val productId: Long,
    val category: ProductFilterCategory
)
