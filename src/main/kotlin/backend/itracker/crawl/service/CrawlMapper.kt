package backend.itracker.crawl.service

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component


@Component
class CrawlMapper {

    fun toMacbook(products: Map<String, DefaultProduct>): List<Macbook> {
        return products.values
            .filter { isMacBook(it) }
            .map(MacbookCrawlResponse.Companion::from)
            .map { it.toDomain() }
            .toList()
    }

    private fun isMacBook(product: DefaultProduct): Boolean {
        return product.name.contains("맥북") && !product.name.contains("정품")
    }
}
