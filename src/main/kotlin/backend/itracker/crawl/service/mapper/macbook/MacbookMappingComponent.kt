package backend.itracker.crawl.service.mapper.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.vo.DefaultProduct

interface MacbookMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): Macbook
}
