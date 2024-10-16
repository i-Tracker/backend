package backend.itracker.tracker.favorite.domain.repository

import backend.itracker.tracker.favorite.domain.Favorite
import backend.itracker.tracker.favorite.domain.FavoriteProduct
import backend.itracker.tracker.member.domain.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface FavoriteRepository : JpaRepository<Favorite, Long> {

    @Query(
        """
        select f
        from Favorite f
        where f.member.id = :memberId
            and f.product = :product
        """
    )
    fun findByFavorite(
        @Param("memberId") memberId: Long,
        @Param("product") product: FavoriteProduct,
    ): Optional<Favorite>

    fun findAllByMember(member: Member, pageable: Pageable): Page<Favorite>

    @Query(
        """
            select count(f)
            from Favorite f
            where f.product = :product
        """
    )
    fun findCountByProduct(@Param("product") favoriteProduct: FavoriteProduct): Long

    @Query(
        """
            select f
            from Favorite f
            join fetch f.member
        """
    )
    fun findAllFetch(): List<Favorite>
}
