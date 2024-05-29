package backend.itracker.crawl.service.mapper.airpods

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class AirPodsMapper(
    private val airPodsMappers: List<AirPodsMappingComponent>
) {

    fun toDomain(filteredProducts: List<DefaultProduct>): List<AirPods> {
        val airPodses = mutableListOf<AirPods>()
        for (product in filteredProducts) {
            try {
                for (airPodsMapper in airPodsMappers) {
                    if (airPodsMapper.supports(product.subCategory)) {
                        airPodses.add(airPodsMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |AirPods Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return airPodses
    }
}
