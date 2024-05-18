package backend.itracker.crawl.service

import backend.itracker.crawl.domain.MacBook
import backend.itracker.crawl.response.DefaultProduct
import backend.itracker.crawl.service.macbook.MacBookCrawlResponse
import org.springframework.stereotype.Component


@Component
class CrawlMapper {

    fun toMacBook(products: Map<String, DefaultProduct>): List<MacBook> {
        return products.values
            .filter { isMacBook(it) }
            .map(MacBookCrawlResponse.Companion::of)
            .map { it.toDomain() }
            .toList()
    }

    private fun isMacBook(product: DefaultProduct): Boolean {
        return product.name.contains("맥북") && !product.name.contains("정품")
    }
}
