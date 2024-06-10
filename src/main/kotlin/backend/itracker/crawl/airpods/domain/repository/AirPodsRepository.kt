package backend.itracker.crawl.airpods.domain.repository

import backend.itracker.crawl.airpods.domain.AirPods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

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
}
