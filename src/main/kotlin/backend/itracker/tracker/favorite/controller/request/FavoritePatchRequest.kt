package backend.itracker.tracker.favorite.controller.request

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.favorite.service.vo.FavoriteInfo

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

