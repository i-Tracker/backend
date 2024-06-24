package backend.itracker.schedule.service.schedulers.ipad

import backend.itracker.crawl.ipad.service.IpadService
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.schedulers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class IpadScheduler(
    private val ipadService: IpadService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.IPAD
    }

    override fun doSchedule() {
        logger.info { "아이패드 크롤링 시작. " }
        val times = measureTime {
            val ipads = crawlService.crawlIpad()
            ipadService.saveAll(ipads)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.IPAD)
        logger.info { "아이패드 크롤링 끝. 시간: $times" }
    }
}
