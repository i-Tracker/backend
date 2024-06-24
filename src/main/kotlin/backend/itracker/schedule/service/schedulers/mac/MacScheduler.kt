package backend.itracker.schedule.service.schedulers.mac

import backend.itracker.crawl.mac.service.MacService
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.schedulers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class MacScheduler(
    private val macService: MacService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.MAC
    }

    override fun doSchedule() {
        logger.info { "맥 크롤링 시작. " }
        val times = measureTime {
            val macs = crawlService.crawlMac()
            macService.saveAll(macs)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.MAC)
        logger.info { "맥 크롤링 끝. 시간: $times" }
    }
}
