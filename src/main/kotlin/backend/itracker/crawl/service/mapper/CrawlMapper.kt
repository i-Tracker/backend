package backend.itracker.crawl.service.mapper

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.mapper.ipad.IpadMappers
import backend.itracker.crawl.service.mapper.macbook.MacbookMappers
import backend.itracker.crawl.service.mapper.watch.AppleWatchMappers
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import org.springframework.stereotype.Component


@Component
class CrawlMapper(
    private val macbookMappers: MacbookMappers,
    private val ipadMappers: IpadMappers,
    private val appleWatchMappers: AppleWatchMappers
) {

    fun toMacbook(products: Map<String, DefaultProduct>): List<Macbook> {
        val filteredProducts = products.values.filter { it.isMacBook() }

        return macbookMappers.toDomain(filteredProducts)
    }

    fun toIpad(products: Map<String, DefaultProduct>): List<Ipad> {
        val filteredProducts = products.values.filter { it.isIpad() }

        return ipadMappers.toDomain(filteredProducts)
    }

    fun toAppleWatch(products: Map<String, DefaultProduct>): List<AppleWatch> {
        val filteredProducts = products.values.filter { it.isAppleWatch() }

        return appleWatchMappers.toDomain(filteredProducts)
    }
}
