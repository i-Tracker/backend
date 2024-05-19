package backend.itracker.crawl.service

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.util.Crawler
import org.springframework.stereotype.Component


@Component
class CrawlService(
    private val crawlMapper: CrawlMapper,
    private val crawler: Crawler
) {

    fun crawlMacbook(category: Category): List<Macbook> {
        val url = "https://pages.coupang.com/p/${category.categoryId}"
        val products = crawler.crawl(url)
        return crawlMapper.toMacbook(products)
    }
}
