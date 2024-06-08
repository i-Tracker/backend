package backend.itracker.schedule.controller

import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.CrawlSchedulerService
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val crawlSchedulerService: CrawlSchedulerService
) {

    @Async
    @PostMapping("/api/v1/schedule/update")
    fun manualCrawl(@RequestParam category: CrawlTargetCategory) {
        when (category) {
            CrawlTargetCategory.MACBOOK -> crawlSchedulerService.crawlMacbook()
            CrawlTargetCategory.IPAD -> crawlSchedulerService.crawlIpad()
            CrawlTargetCategory.APPLE_WATCH -> crawlSchedulerService.crawlAppleWatch()
            CrawlTargetCategory.MAC -> crawlSchedulerService.crawlMac()
            CrawlTargetCategory.AIRPODS -> crawlSchedulerService.crawlAirPods()
            CrawlTargetCategory.IPHONE -> crawlSchedulerService.crawlIphone()
        }
    }
}
