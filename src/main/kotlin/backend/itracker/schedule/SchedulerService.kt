package backend.itracker.schedule

import backend.itracker.crawl.service.Category
import backend.itracker.crawl.service.CrawlService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

@Component
class SchedulerService(
    private val crawlService: CrawlService,
    private val macbookService: MacbookService
) {

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    fun crawlMacbook() {
        logger.info { "맥북 크롤링 시작. " }
        val times = measureTime {
            val macbooks = crawlService.crawlMacbook(Category.MACBOOK)
            macbookService.saveAll(macbooks)
        }
        logger.info { "맥북 크롤링 끝. 시간: $times" }
    }
}
