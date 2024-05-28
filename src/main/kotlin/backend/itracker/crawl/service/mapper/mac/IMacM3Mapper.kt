package backend.itracker.crawl.service.mapper.mac

import backend.itracker.crawl.mac.domain.Mac
import backend.itracker.crawl.mac.domain.MacCategory
import backend.itracker.crawl.service.response.MacCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val IMAC_M3_24 = "2023 iMac 24형 M3"
private const val IMAC_M3_24_CTO = "2023 iMac 24형 M3 CTO"

@Component
class IMacM3Mapper : MacMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return IMAC_M3_24 == subCategory || IMAC_M3_24_CTO == subCategory
    }

    override fun toDomain(product: DefaultProduct): Mac {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1]
        val category = MacCategory.IMAC
        val size = title[3]
        val color = names[1]
        val gpu = names[2].replace("코어", "")
        val storage = names[3]
        val chipAndCpu = names[4].split(" ")
        val chip = chipAndCpu.first()
        val cpu = chipAndCpu.last().replace("코어", "")
        val memory = names[5]

        return MacCrawlResponse(
            coupangId = product.productId,
            company = company,
            name = product.name,
            category = category,
            chip = chip,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            color = color,
            size = size,
            releaseYear = releaseYear,
            discountPercentage = product.price.discountPercentage,
            basePrice = product.price.basePrice,
            discountPrice = product.price.discountPrice,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
            isOutOfStock = product.price.isOutOfStock
        ).toDomain()
    }
}
