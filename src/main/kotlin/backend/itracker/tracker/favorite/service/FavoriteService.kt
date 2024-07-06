package backend.itracker.tracker.favorite.service

import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import backend.itracker.tracker.favorite.domain.repository.FavoriteRepository
import backend.itracker.tracker.favorite.service.handler.FavoriteComposite
import backend.itracker.tracker.favorite.service.handler.response.CommonFavoriteProductModel
import backend.itracker.tracker.favorite.service.vo.FavoriteCount
import backend.itracker.tracker.member.domain.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteComposite: FavoriteComposite,
) {

    fun patchFavorite(favorite: Favorite) {
        val maybeFavorite =
            favoriteRepository.findByFavorite(favorite.member.id, favorite.product)

        maybeFavorite.ifPresentOrElse(
            { favoriteRepository.delete(it) },
            { favoriteRepository.save(favorite) }
        )
    }

    @Transactional(readOnly = true)
    fun findAllFavoritesByMember(
        member: Member, pageable: Pageable
    ): Page<CommonFavoriteProductModel> {
        val pageFavorites = favoriteRepository.findAllByMember(member, pageable)
        val favorites = pageFavorites.content
            .groupBy { it.product.productCategory }
        val productsContents = favorites.flatMap { (productCategory, favorites) ->
            favoriteComposite.findAllByIds(productCategory, favorites)
        }
            .sortedBy { it.createdAt }
            .reversed().toList()

        return PageImpl(productsContents, pageFavorites.pageable, productsContents.size.toLong())
    }

    @Transactional(readOnly = true)
    fun findFavoritesCountByFavoriteProduct(
        favoriteProduct: FavoriteProduct
    ): FavoriteCount {
        val count = favoriteRepository.findCountByProduct(favoriteProduct)
        return FavoriteCount(
            productId = favoriteProduct.productId,
            category = favoriteProduct.productCategory,
            count = count
        )
    }
}
