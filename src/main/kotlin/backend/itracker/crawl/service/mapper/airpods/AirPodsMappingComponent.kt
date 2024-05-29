package backend.itracker.crawl.service.mapper.airpods

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.service.vo.DefaultProduct

interface AirPodsMappingComponent {

    fun supports(subCategory: String): Boolean

    fun toDomain(product: DefaultProduct): AirPods
}
