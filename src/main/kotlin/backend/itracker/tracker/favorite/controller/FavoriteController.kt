package backend.itracker.tracker.favorite.controller

import backend.itracker.tracker.common.request.PageParams
import backend.itracker.tracker.common.response.Pages
import backend.itracker.tracker.favorite.controller.request.FavoritePatchRequest
import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import backend.itracker.tracker.favorite.service.FavoriteService
import backend.itracker.tracker.favorite.service.handler.response.CommonFavoriteProductModel
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.resolver.LoginMember
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val FAVORITE_DEFAULT_SIZE = 6

@RestController
class FavoriteController(
    private val favoriteService: FavoriteService
) {

    @PatchMapping("/api/v1/favorites")
    fun patchFavorites(
        @LoginMember member: Member,
        @RequestBody request: FavoritePatchRequest
    ): ResponseEntity<Unit> {
        val favoriteInfo = request.toFavoriteInfo()
        val favorite = Favorite(
            member = member,
            product = FavoriteProduct.of(favoriteInfo.productId, favoriteInfo.category)
        )
        favoriteService.patchFavorite(favorite)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/api/v1/favorites")
    fun findAllFavorites(
        @LoginMember member: Member,
        @ModelAttribute pageParams: PageParams,
    ): ResponseEntity<Pages<CommonFavoriteProductModel>> {
        val favorites = favoriteService.findAllFavoritesByMember(
            member, PageRequest.of(pageParams.offset, FAVORITE_DEFAULT_SIZE)
        )

        return ResponseEntity.ok(Pages.withPagination(favorites))
    }
}
