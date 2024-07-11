package backend.itracker.crawl.result.repository

import backend.itracker.crawl.result.CrawlResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface CrawlResultRepository : JpaRepository<CrawlResult, Long> {

    @Query("""
        select cr
        from CrawlResult cr
        where cr.createdAt between :today and :tomorrow
    """)
    fun findByCreatedAtBetween(today: LocalDateTime, tomorrow: LocalDateTime) : List<CrawlResult>

    @Query("""
        select cr
        from CrawlResult cr
        order by cr.createdAt desc
        limit 1
    """)
    fun findRecentCrawlResult(): CrawlResult
}
