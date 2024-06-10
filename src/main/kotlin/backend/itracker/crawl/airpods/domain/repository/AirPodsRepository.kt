package backend.itracker.crawl.airpods.domain.repository

import backend.itracker.crawl.airpods.domain.AirPods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun AirPodsRepository.getByIdAllFetch(id: Long): AirPods {
    return findByIdAllFetch(id).getOrNull() ?: throw NoSuchElementException("해당하는 id의 AirPods가 없습니다. id: $id")
}

interface AirPodsRepository : JpaRepository<AirPods, Long> {

    @Query(
        """
            select a
            from AirPods a
            join fetch a.prices
            where a.coupangId = :coupangId
        """
    )
    fun findByCoupangId(@Param("coupangId") coupangId: Long): Optional<AirPods>

    @Query(
        """
            select a
            from AirPods a
            join fetch a.prices
        """
    )
    fun findAllFetch(): List<AirPods>

    @Query(
        """
            select a
            from AirPods a
            join fetch a.prices
            where a.id = :id
    """
    )
    fun findByIdAllFetch(@Param("id") id: Long): Optional<AirPods>
}
