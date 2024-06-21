package backend.itracker.crawl.service.mapper.ipad

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class IpadMappers(
    private val ipadMappers: List<IpadMappingComponent>
) {

    fun toDomain(filteredProducts: List<DefaultProduct>): List<Ipad> {
        val ipads = mutableListOf<Ipad>()
        for (product in filteredProducts) {
            try {
                for (ipadMapper in ipadMappers) {
                    if (ipadMapper.supports(product.subCategory)) {
                        ipads.add(ipadMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |Ipad Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return ipads
    }
}
