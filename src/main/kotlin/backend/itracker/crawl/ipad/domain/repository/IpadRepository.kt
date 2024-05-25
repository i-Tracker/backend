package backend.itracker.crawl.ipad.domain.repository

import backend.itracker.crawl.ipad.domain.Ipad
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface IpadRepository : JpaRepository<Ipad, Long> {

    @Query("""
        select i 
        from Ipad i
        join fetch i.prices
        where i.coupangId = :coupangId
    """)
    fun findByCoupangId(@Param("coupangId") coupangId: Long): Optional<Ipad>
}

