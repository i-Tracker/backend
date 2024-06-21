package backend.itracker.tracker.member.service.vo

import backend.itracker.crawl.common.ProductCategory

data class FavoriteInfo(
    val productId: Long,
    val category: ProductCategory
)
