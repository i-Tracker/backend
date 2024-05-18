package backend.itracker.crawl.service

import backend.itracker.crawl.domain.Macbook
import backend.itracker.crawl.service.common.Crawler
import org.springframework.stereotype.Component


@Component
class CrawlService(
    private val crawlMapper: CrawlMapper,
    private val crawler: Crawler
) {
    fun crawlMacBook(category: Category): List<Macbook> {
        val url = "https://pages.coupang.com/p/${category.categoryId}"
        val products = crawler.crawl(url)

        return crawlMapper.toMacBook(products)
    }
}
