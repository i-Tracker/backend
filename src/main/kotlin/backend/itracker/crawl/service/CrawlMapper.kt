package backend.itracker.crawl.service

import backend.itracker.crawl.domain.MacBook
import backend.itracker.crawl.response.DefaultProduct
import backend.itracker.crawl.service.macbook.MacBookCrawlResponse
import org.springframework.stereotype.Component


@Component
class CrawlMapper {

    fun toMacBook(products: Map<String, DefaultProduct>): List<MacBook> {
        return products.values
            .map(MacBookCrawlResponse.Companion::of)
            .map { it.toDomain() }
            .toList()
    }
}
