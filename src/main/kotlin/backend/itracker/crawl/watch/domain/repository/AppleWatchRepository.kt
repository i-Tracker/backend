package backend.itracker.crawl.watch.domain.repository

import backend.itracker.crawl.watch.domain.AppleWatch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface AppleWatchRepository : JpaRepository<AppleWatch, Long> {

    @Query("""
        select aw 
        from AppleWatch aw
        join fetch aw.prices
        where aw.coupangId = :coupangId
    """)
    fun findByCoupangId(@Param("coupangId") coupangId: Long): Optional<AppleWatch>
}
