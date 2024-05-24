package backend.itracker.crawl.macbook.domain.repository

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

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
    fun findAllFetchByProductCategory(@Param("category") crawlTargetCategory: ProductCategory): List<Macbook>
}
