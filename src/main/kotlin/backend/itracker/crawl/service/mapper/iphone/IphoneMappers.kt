package backend.itracker.crawl.service.mapper.iphone

import backend.itracker.crawl.exception.CrawlException
import backend.itracker.crawl.iphone.domain.Iphone
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class IphoneMappers(
    private val iphoneMappers: List<IphoneMappingComponent>
) {

    fun toDomain(products: List<DefaultProduct>): List<Iphone> {
        val iphones = mutableListOf<Iphone>()
        for (product in products) {
            try {
                for (iphoneMapper in iphoneMappers) {
                    if (iphoneMapper.supports(product.subCategory)) {
                        iphones.add(iphoneMapper.toDomain(product))
                    }
                }
            } catch (e: Exception) {
                throw CrawlException(
                    """
                    |Iphone Mapping 중에 에러가 발생했습니다. 
                    |name : ${product.name}, 
                    |subcategory : ${product.subCategory}, 
                    |error: ${e.stackTraceToString()}
                    |""".trimMargin()
                )
            }
        }

        return iphones
    }
}
