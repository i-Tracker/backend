package backend.itracker.tracker.member.controller.request

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.member.service.vo.FavoriteInfo

data class FavoritePatchRequest(
    val productId: Long,
    val productCategory: String
) {

    fun toFavoriteInfo() : FavoriteInfo
    {
        return FavoriteInfo(
            productId = productId,
            category = ProductFilterCategory.valueOf(productCategory.uppercase())
        )
    }
}

