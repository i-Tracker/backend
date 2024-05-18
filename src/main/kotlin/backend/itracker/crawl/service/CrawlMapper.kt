package backend.itracker.crawl.service

import backend.itracker.crawl.domain.Macbook
import backend.itracker.crawl.response.DefaultProduct
import backend.itracker.crawl.service.macbook.MacbookCrawlResponse
import org.springframework.stereotype.Component


@Component
class CrawlMapper {

    fun toMacbook(products: Map<String, DefaultProduct>): List<Macbook> {
        return products.values
            .filter { isMacBook(it) }
            .map(MacbookCrawlResponse.Companion::of)
            .map { it.toDomain() }
            .toList()
    }

    private fun isMacBook(product: DefaultProduct): Boolean {
        return product.name.contains("맥북") && !product.name.contains("정품")
    }
}
