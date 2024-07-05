package backend.itracker.tracker.member.service.handler.airpods

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.common.ProductCategory
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.domain.repository.FavoriteRepository
import backend.itracker.tracker.member.service.handler.FavoriteHandleable
import backend.itracker.tracker.member.service.handler.response.AirpodsFavoriteResponse
import backend.itracker.tracker.member.service.handler.response.CommonFavoriteProductModel
import org.springframework.stereotype.Component

@Component
class FavoriteAirpodsHandler(
    private val airpodsService: AirPodsService,
    private val favoriteRepository: FavoriteRepository,
) : FavoriteHandleable {

    override fun supports(category: ProductCategory): Boolean {
        return ProductCategory.AIRPODS == category
    }

    override fun findAllByIds(favorites: List<Favorite>): List<CommonFavoriteProductModel> {
        val airpods = airpodsService.findAllInIds(favorites.map { it.product.productId }
            .toList())

        return airpods.map { airpod ->
            val favorite = favorites.find { it.product.productId == airpod.id }
                ?: throw IllegalStateException("찜한 상품을 찾을 수 없습니다.")

            val notificationCount =
                favoriteRepository.findCountByProduct(favorite.product)
            AirpodsFavoriteResponse.of(airpod, notificationCount, favorite)
        }
    }
}
