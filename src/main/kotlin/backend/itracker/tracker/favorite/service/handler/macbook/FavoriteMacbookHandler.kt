package backend.itracker.tracker.favorite.service.handler.macbook

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.domain.repository.FavoriteRepository
import backend.itracker.tracker.favorite.service.handler.FavoriteHandleable
import backend.itracker.tracker.favorite.service.handler.response.CommonFavoriteProductModel
import backend.itracker.tracker.favorite.service.handler.response.MacbookFavoriteResponse
import org.springframework.stereotype.Component

@Component
class FavoriteMacbookHandler(
    private val macbookService: MacbookService,
    private val favoriteRepository: FavoriteRepository,
) : FavoriteHandleable {

    override fun supports(category: ProductCategory): Boolean {
        return category == ProductCategory.MACBOOK
    }

    override fun findAllByIds(favorites: List<Favorite>): List<CommonFavoriteProductModel> {
        val macbooks = macbookService.findAllInIds(favorites.map { it.product.productId }
            .toList())

        return macbooks.map { macbook ->
            val favorite = favorites.find { it.product.productId == macbook.id }
                ?: throw IllegalStateException("찜한 상품을 찾을 수 없습니다.")

            val notificationCount =
                favoriteRepository.findCountByProduct(favorite.product)

            MacbookFavoriteResponse.of(macbook, notificationCount, favorite)
        }
    }

    override fun findAllDecreasingPrice(favorites: List<Favorite>): List<Favorite> {
        return macbookService.findAllInIds(favorites.map { it.product.productId }
            .toList())
            .filter { it.isDecreasingPrice() }
            .map { macbook ->
                favorites.find { it.product.productId == macbook.id }
                    ?: throw IllegalStateException("찜한 상품을 찾을 수 없습니다.")
            }
    }
}
