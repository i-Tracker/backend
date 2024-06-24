package backend.itracker.schedule.service.crawlers

import backend.itracker.crawl.service.CrawlTargetCategory
import org.springframework.stereotype.Component

@Component
class CrawlComposite(
    private val schedulables: Set<Schedulable>
) {

    private val schedulers = schedulables.associateBy { it.supports() }

    fun getScheduler(crawlTargetCategory: CrawlTargetCategory): Schedulable {
        return schedulers[crawlTargetCategory] ?: throw IllegalStateException("해당하는 스케쥴러가 없습니다. $crawlTargetCategory")
    }
}
