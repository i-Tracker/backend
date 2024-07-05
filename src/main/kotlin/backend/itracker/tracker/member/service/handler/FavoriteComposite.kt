package backend.itracker.tracker.member.service.handler

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.service.handler.response.CommonFavoriteProductModel
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
}
