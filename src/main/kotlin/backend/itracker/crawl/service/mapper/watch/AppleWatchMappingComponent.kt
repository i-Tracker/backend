package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch

interface AppleWatchMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): AppleWatch
}
