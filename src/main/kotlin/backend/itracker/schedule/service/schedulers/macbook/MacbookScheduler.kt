package backend.itracker.schedule.service.schedulers.macbook

import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.schedulers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class MacbookScheduler(
    private val macbookService: MacbookService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.MACBOOK
    }

    override fun doSchedule() {
        logger.info { "맥북 크롤링 시작. " }
        val times = measureTime {
            val macbooks = crawlService.crawlMacbook()
            macbookService.saveAll(macbooks)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.MACBOOK)
        logger.info { "맥북 크롤링 끝. 시간: $times" }
    }
}
