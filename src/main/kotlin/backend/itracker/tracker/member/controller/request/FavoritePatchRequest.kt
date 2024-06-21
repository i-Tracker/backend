package backend.itracker.tracker.member.controller.request

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.member.service.vo.FavoriteInfo

data class FavoritePatchRequest(
    val productId: Long,
    val productCategory: String
) {

    fun toFavoriteInfo() : FavoriteInfo
    {
        return FavoriteInfo(
            productId = productId,
            category = ProductCategory.valueOf(productCategory.uppercase())
        )
    }
}

