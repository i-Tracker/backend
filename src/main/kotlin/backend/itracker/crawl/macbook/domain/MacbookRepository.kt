package backend.itracker.crawl.macbook.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MacbookRepository: JpaRepository<Macbook, Long> {

    fun findByName(name: String): Optional<Macbook>

    @Query(
        """
            select m
            from Macbook m
            join fetch m.prices
        """
    )
    fun findAllFetch(): List<Macbook>
}
