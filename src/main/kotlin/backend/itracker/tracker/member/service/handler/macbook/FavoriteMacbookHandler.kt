package backend.itracker.tracker.member.service.handler.macbook

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.service.handler.FavoriteHandleable
import backend.itracker.tracker.member.service.handler.response.CommonFavoriteProductModel
import backend.itracker.tracker.member.service.handler.response.MacbookFavoriteResponse
import org.springframework.stereotype.Component

@Component
class FavoriteMacbookHandler(
    private val macbookService: MacbookService
) : FavoriteHandleable {
    override fun supports(category: ProductCategory): Boolean {
        return category == ProductCategory.MACBOOK_PRO || category == ProductCategory.MACBOOK_AIR
    }

    override fun findAllByIds(favorites: List<Favorite>): List<CommonFavoriteProductModel> {
        val macbooks = macbookService.findAllInIds(favorites.map { it.product.productId }
            .toList())

        return macbooks.map { macbook ->
            val favorite = favorites.find { it.product.productId == macbook.id }
                ?: throw IllegalStateException("찜한 상품을 찾을 수 없습니다.")

            MacbookFavoriteResponse.of(macbook, favorite)
        }
    }
}
