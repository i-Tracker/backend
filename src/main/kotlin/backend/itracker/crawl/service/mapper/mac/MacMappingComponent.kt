package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.service.vo.DefaultProduct

interface MacMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): Mac
}
