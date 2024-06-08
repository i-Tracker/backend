package backend.itracker.crawl.service.mapper.iphone

import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.service.vo.DefaultProduct

interface IphoneMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): Iphone
}
