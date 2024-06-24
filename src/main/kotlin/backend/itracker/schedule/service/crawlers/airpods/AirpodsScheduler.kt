package backend.itracker.schedule.service.crawlers.airpods

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.crawlers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class AirpodsScheduler(
    private val airPodsService: AirPodsService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.AIRPODS
    }

    override fun doSchedule() {
        logger.info { "에어팟 크롤링 시작. " }
        val times = measureTime {
            val airPods = crawlService.crawlAirPods()
            airPodsService.saveAll(airPods)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.AIRPODS)
        logger.info { "에어팟 크롤링 끝. 시간: $times" }
    }
}
