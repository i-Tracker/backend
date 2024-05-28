package backend.itracker.crawl.mac.domain.repository

import backend.itracker.crawl.mac.domain.Mac
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MacRepository : JpaRepository<Mac, Long> {

    @Query(
        """
        select m
        from Mac m
        join fetch m.prices
        where m.coupangId = :coupangId
    """
    )
    fun findByCoupangId(@Param("coupangId") coupangId: Long): Optional<Mac>
}
