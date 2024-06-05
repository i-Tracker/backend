package backend.itracker.schedule.service

import backend.itracker.crawl.airpods.service.AirPodsService
import backend.itracker.crawl.ipad.service.IpadService
import backend.itracker.crawl.mac.service.MacService
import backend.itracker.crawl.macbook.service.MacbookService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.watch.service.AppleWatchService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.time.measureTime

private const val CRAWLING_TIME = "0 0 3 * * *"
private const val TIME_ZONE = "Asia/Seoul"

private val logger = KotlinLogging.logger {}

@Component
class CrawlSchedulerService(
    private val crawlService: CrawlService,
    private val macbookService: MacbookService,
    private val ipadService: IpadService,
    private val appleWatchService: AppleWatchService,
    private val macService: MacService,
    private val airPodsService: AirPodsService
) {

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun crawlMacbook() {
        logger.info { "맥북 크롤링 시작. " }
        val times = measureTime {
            val macbooks = crawlService.crawlMacbook()
            macbookService.saveAll(macbooks)
        }
        logger.info { "맥북 크롤링 끝. 시간: $times" }
    }

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun crawlIpad() {
        logger.info { "아이패드 크롤링 시작. " }
        val times = measureTime {
            val ipads = crawlService.crawlIpad()
            ipadService.saveAll(ipads)
        }
        logger.info { "아이패드 크롤링 끝. 시간: $times" }
    }

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun crawlAppleWatch() {
        logger.info { "애플워치 크롤링 시작. " }
        val times = measureTime {
            val appleWatches = crawlService.crawlAppleWatch()
            appleWatchService.saveAll(appleWatches)
        }
        logger.info { "애플워치 크롤링 끝. 시간: $times" }
    }

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun crawlMac() {
        logger.info { "맥 크롤링 시작. " }
        val times = measureTime {
            val macs = crawlService.crawlMac()
            macService.saveAll(macs)
        }
        logger.info { "맥 크롤링 끝. 시간: $times" }
    }

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun crawlAirPods() {
        logger.info { "에어팟 크롤링 시작. " }
        val times = measureTime {
            val airPods = crawlService.crawlAirPods()
            airPodsService.saveAll(airPods)
        }
        logger.info { "에어팟 크롤링 끝. 시간: $times" }
    }
}
