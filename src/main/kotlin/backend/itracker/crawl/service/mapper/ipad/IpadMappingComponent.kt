package backend.itracker.crawl.service.mapper.ipad

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.service.vo.DefaultProduct

interface IpadMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): Ipad
}
