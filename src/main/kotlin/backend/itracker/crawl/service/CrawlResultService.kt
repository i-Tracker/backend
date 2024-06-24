package backend.itracker.crawl.service

import backend.itracker.crawl.result.CrawlResult
import backend.itracker.crawl.result.repository.CrawlResultRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class CrawlResultService(
    private val crawlResultRepository: CrawlResultRepository
) {

    fun updateCrawlResult(crawlCategory: CrawlTargetCategory) {
        val today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
        val results = crawlResultRepository.findByCreatedAtBetween(today, today.plusDays(1L))

        if (results.isEmpty()) {
            crawlResultRepository.save(CrawlResult.from(crawlCategory))
            return
        }

        val latest = results.last()
        latest.update(CrawlResult.from(crawlCategory))
    }
}
