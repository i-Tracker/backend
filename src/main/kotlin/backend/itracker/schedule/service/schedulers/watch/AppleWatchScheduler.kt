package backend.itracker.schedule.service.schedulers.watch

import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.crawl.watch.service.AppleWatchService
import backend.itracker.schedule.service.schedulers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class AppleWatchScheduler(
    private val appleWatchService: AppleWatchService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.APPLE_WATCH
    }

    override fun doSchedule() {
        logger.info { "애플워치 크롤링 시작. " }
        val times = measureTime {
            val appleWatches = crawlService.crawlAppleWatch()
            appleWatchService.saveAll(appleWatches)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.APPLE_WATCH)
        logger.info { "애플워치 크롤링 끝. 시간: $times" }
    }
}
