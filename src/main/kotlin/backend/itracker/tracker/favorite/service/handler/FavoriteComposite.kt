package backend.itracker.tracker.favorite.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.service.handler.response.CommonFavoriteProductModel
import org.springframework.stereotype.Component

@Component
class FavoriteComposite(
    private val favoriteHandlers: List<FavoriteHandleable>
) {

    fun findAllByIds(
        category: ProductCategory,
        favorites: List<Favorite>
    ) : List<CommonFavoriteProductModel> {
        val favoriteHandler = favoriteHandlers.find { it.supports(category) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: $category")

        return favoriteHandler.findAllByIds(favorites)
    }

    fun findAllDecreasingPrice(category: ProductCategory, favorites: List<Favorite>): List<Favorite> {
        val favoriteHandler = favoriteHandlers.find { it.supports(category) }
            ?: throw IllegalArgumentException("핸들러가 지원하지 않는 카테고리 입니다. category: $category")

        return favoriteHandler.findAllDecreasingPrice(favorites)
    }
}
