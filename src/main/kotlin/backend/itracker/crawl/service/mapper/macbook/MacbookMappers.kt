package backend.itracker.crawl.service.mapper.macbook

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class MacbookMappers(
    private val macbookMappers: List<MacbookMappingComponent>
) {

    fun toDomain(products: List<DefaultProduct>): List<Macbook> {
        val macbooks = mutableListOf<Macbook>()
        for (product in products) {
            try {
                for (macbookMapper in macbookMappers) {
                    if (macbookMapper.supports(product.subCategory)) {
                        macbooks.add(macbookMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |Macbook Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return macbooks
    }
}
