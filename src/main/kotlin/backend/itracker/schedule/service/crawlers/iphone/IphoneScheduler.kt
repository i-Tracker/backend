package backend.itracker.schedule.service.crawlers.iphone

import backend.itracker.crawl.iphone.service.IphoneService
import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.crawlers.Schedulable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class IphoneScheduler(
    private val iphoneService: IphoneService,
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) : Schedulable(crawlResultService, crawlService) {

    override fun supports(): CrawlTargetCategory {
        return CrawlTargetCategory.IPHONE
    }

    override fun doSchedule() {
        logger.info { "아이폰 크롤링 시작. " }
        val times = measureTime {
            val iphones = crawlService.crawlIphone()
            iphoneService.saveAll(iphones)
        }
        crawlResultService.updateCrawlResult(CrawlTargetCategory.IPHONE)
        logger.info { "아이폰 크롤링 끝. 시간: $times" }
    }
}
