package backend.itracker.tracker.member.controller

import backend.itracker.tracker.member.controller.request.FavoritePatchRequest
import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.domain.FavoriteProduct
import backend.itracker.tracker.member.domain.Member
import backend.itracker.tracker.member.service.FavoriteService
import backend.itracker.tracker.resolver.LoginMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
            product = FavoriteProduct(productId = favoriteInfo.productId, productCategory = favoriteInfo.category)
        )
        favoriteService.patchFavorite(favorite)

        return ResponseEntity.noContent().build()
    }
}
