package backend.itracker.schedule.controller

import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.SchedulerService
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val schedulerService: SchedulerService
) {
    @Async
    @PostMapping("/api/v1/schedule/update")
    fun manualCrawl(@RequestParam category: CrawlTargetCategory) {
        when (category) {
            CrawlTargetCategory.MACBOOK -> schedulerService.crawlMacbook()
            CrawlTargetCategory.IPAD -> schedulerService.crawlIpad()
            CrawlTargetCategory.APPLE_WATCH -> schedulerService.crawlAppleWatch()
            CrawlTargetCategory.MAC -> schedulerService.crawlMac()
            else -> throw IllegalArgumentException("수동 업데이트를 지원하지 않는 카테고리 입니다.")
        }
    }
}
