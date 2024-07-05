package backend.itracker.tracker.member.controller

import backend.itracker.tracker.common.request.PageParams
import backend.itracker.tracker.common.response.Pages
import backend.itracker.tracker.member.controller.request.FavoritePatchRequest
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.domain.FavoriteProduct
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.service.FavoriteService
import backend.itracker.tracker.member.service.handler.response.CommonFavoriteProductModel
import backend.itracker.tracker.resolver.LoginMember
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
            product = FavoriteProduct(productId = favoriteInfo.productId, productFilterCategory = favoriteInfo.category)
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
