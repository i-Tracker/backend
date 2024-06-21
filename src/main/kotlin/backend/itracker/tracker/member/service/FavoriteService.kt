package backend.itracker.tracker.member.service

import backend.itracker.tracker.member.domain.Favorite
import backend.itracker.tracker.member.domain.repository.FavoriteRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository
) {

    fun patchFavorite(favorite: Favorite) {
        val logger = KotlinLogging.logger{}
        logger.warn { "patchFavorite: $favorite"}

        val maybeFavorite =
            favoriteRepository.findByFavorite(favorite.member.id, favorite.product)

        maybeFavorite.ifPresentOrElse(
            { favoriteRepository.delete(it) },
            { favoriteRepository.save(favorite) }
        )
    }
}
