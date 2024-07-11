package backend.itracker.schedule.service

import backend.itracker.crawl.service.CrawlTargetCategory
import backend.itracker.schedule.service.event.CrawlEndEvent
import backend.itracker.schedule.service.schedulers.SchedulerComposite
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private const val CRAWLING_TIME = "0 0 3 * * *"
private const val TIME_ZONE = "Asia/Seoul"

private val CRAWL_TARGETS = CrawlTargetCategory.entries

@Component
class CrawlSchedulerService(
    private val crawlers: SchedulerComposite,
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun crawlManual(category: CrawlTargetCategory) {
        crawlers.getScheduler(category).doSchedule()
    }

    @Scheduled(cron = CRAWLING_TIME, zone = TIME_ZONE)
    fun scheduleCrawl() {
        CRAWL_TARGETS.forEach {
            crawlManual(it)
        }

        eventPublisher.publishEvent(CrawlEndEvent())
    }
}
