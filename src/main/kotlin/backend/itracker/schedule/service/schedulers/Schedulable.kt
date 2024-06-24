package backend.itracker.schedule.service.schedulers

import backend.itracker.crawl.service.CrawlResultService
import backend.itracker.crawl.service.CrawlService
import backend.itracker.crawl.service.CrawlTargetCategory

abstract class Schedulable(
    private val crawlResultService: CrawlResultService,
    private val crawlService: CrawlService,
) {

    abstract fun supports(): CrawlTargetCategory

    abstract fun doSchedule()
}

