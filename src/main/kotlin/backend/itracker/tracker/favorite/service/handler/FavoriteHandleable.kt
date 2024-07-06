package backend.itracker.tracker.favorite.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.service.handler.response.CommonFavoriteProductModel

interface FavoriteHandleable {

    fun supports(category: ProductCategory): Boolean

    fun findAllByIds(favorites: List<Favorite>): List<CommonFavoriteProductModel>
}
