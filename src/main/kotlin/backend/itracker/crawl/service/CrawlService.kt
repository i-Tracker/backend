package backend.itracker.crawl.service

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.mapper.CrawlMapper
import backend.itracker.crawl.service.util.Crawler
import backend.itracker.crawl.watch.domain.AppleWatch
import org.springframework.stereotype.Component

private const val CRAWL_URL = "https://pages.coupang.com/p/"

@Component
class CrawlService(
    private val crawlMapper: CrawlMapper,
    private val crawler: Crawler
) {

    fun crawlMacbook(): List<Macbook> {
        val url = getCrawlUrl(CrawlTargetCategory.MACBOOK)
        val products = crawler.crawl(url)
        return crawlMapper.toMacbook(products)
    }

    fun crawlIpad(): List<Ipad> {
        val url = getCrawlUrl(CrawlTargetCategory.IPAD)
        val products = crawler.crawl(url)
        return crawlMapper.toIpad(products)
    }

    fun crawlAppleWatch(): List<AppleWatch> {
        val url = getCrawlUrl(CrawlTargetCategory.APPLE_WATCH)
        val products = crawler.crawl(url)
        return crawlMapper.toAppleWatch(products)
    }

    private fun getCrawlUrl(category: CrawlTargetCategory): String {
        return "$CRAWL_URL${category.categoryId}"
    }
}
