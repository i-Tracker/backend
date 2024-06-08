package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import kotlin.jvm.optionals.getOrNull

fun MacbookRepository.findByIdAllFetch(macbookId: Long): Macbook = findAllPricesByMacbookId(macbookId).getOrNull()
    ?: throw NoSuchElementException("Macbook이 존재하지 않습니다. id: $macbookId")


interface MacbookRepository: JpaRepository<Macbook, Long>, MacbookRepositoryCustom {

    fun findByCoupangId(coupangId: Long): Optional<Macbook>

    @Query(
        """
            select m
            from Macbook m
            join fetch m.prices
            where m.category = :category
        """
    )
    fun findAllFetchByProductCategory(@Param("category") crawlTargetCategory: MacbookCategory): List<Macbook>

    @Query(
        """
            select m
            from Macbook m
            join fetch m.prices
            where m.id = :id
        """
    )
    fun findAllPricesByMacbookId(@Param("id") id: Long): Optional<Macbook>

    fun findByIdBetween(startId: Long, endId: Long): List<Macbook>
}
