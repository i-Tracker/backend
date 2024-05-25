package backend.itracker.crawl.service.mapper

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.mapper.macbook.MacbookMappers
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component


@Component
class CrawlMapper(
    private val macbookMappers: MacbookMappers
) {

    fun toMacbook(products: Map<String, DefaultProduct>): List<Macbook> {
        val filteredProducts = products.values.filter { it.isMacBook() }

        return macbookMappers.toDomain(filteredProducts)
    }
}
