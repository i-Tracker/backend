package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class MacMappers(
    private val macMappers: List<MacMappingComponent>
) {

    fun toDomain(products: List<DefaultProduct>): List<Mac> {
        val macs = mutableListOf<Mac>()
        for (product in products) {
            try {
                for (macMapper in macMappers) {
                    if (macMapper.supports(product.subCategory)) {
                        macs.add(macMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |Mac Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return macs
    }
}
