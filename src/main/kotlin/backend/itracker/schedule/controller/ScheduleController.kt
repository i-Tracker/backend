package backend.itracker.schedule.controller

import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.SchedulerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val schedulerService: SchedulerService
) {

    @PostMapping("/api/v1/schedule/update")
    fun manualCrawl(@RequestParam category: CrawlTargetCategory): ResponseEntity<Unit> {
        when (category) {
            CrawlTargetCategory.MACBOOK -> schedulerService.crawlMacbook()
            CrawlTargetCategory.IPAD -> schedulerService.crawlIpad()
            CrawlTargetCategory.APPLE_WATCH -> schedulerService.crawlAppleWatch()
            else -> return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok().build()
    }
}
