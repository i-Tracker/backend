package backend.itracker.tracker.member.service.handler

import backend.itracker.crawl.common.ProductFilterCategory
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.service.handler.response.CommonFavoriteProductModel

interface FavoriteHandleable {

    fun supports(category: ProductFilterCategory): Boolean

    fun findAllByIds(favorites: List<Favorite>): List<CommonFavoriteProductModel>
}
