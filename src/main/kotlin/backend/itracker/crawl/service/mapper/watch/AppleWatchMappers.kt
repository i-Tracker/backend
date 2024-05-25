package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import org.springframework.stereotype.Component

@Component
class AppleWatchMappers(
    private val appleWatchMappers: List<AppleWatchMappingComponent>
) {
    fun toDomain(products: List<DefaultProduct>): List<AppleWatch> {
        val appleWatches = mutableListOf<AppleWatch>()
        for (product in products) {
            try {
                for (appleWatchMapper in appleWatchMappers) {
                    if (appleWatchMapper.supports(product.subCategory)) {
                        appleWatches.add(appleWatchMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |AppleWatch Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return appleWatches
    }

}
