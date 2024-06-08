package backend.itracker.crawl.iphone.domain.repository

import backend.itracker.crawl.iphone.domain.Iphone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface IphoneRepository : JpaRepository<Iphone, Long> {

    @Query(
        """
            select i
            from Iphone i
            join fetch i.prices
            where i.coupangId = :coupangId
        """
    )
    fun findByCoupangId(@Param("coupangId") coupangId: Long): Optional<Iphone>
}

