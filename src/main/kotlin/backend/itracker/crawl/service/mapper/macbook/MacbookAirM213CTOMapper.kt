package backend.itracker.crawl.service.mapper.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_BOOK_AIR_M2_13_CTO = "MacBook Air 13 M2 CTO"

@Component
class MacbookAirM213CTOMapper : MacbookMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return subCategory == MAC_BOOK_AIR_M2_13_CTO
    }

    override fun toDomain(product: DefaultProduct): Macbook {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1].toInt()
        val size = title[4].toInt()
        val color = names[1]
        val processor = names[2].split(" ")
        val chip = processor.first()
        val cpu = processor.last()
        val gpuProcessor = names[3].split(" ")
        val gpu = if (gpuProcessor.first() == "GPU") {
            gpuProcessor[1]
        } else {
            gpuProcessor.first()
        }
        val storage = names[4]
        val memory = names[5]
        val language = names[7]

        return MacbookCrawlResponse(
            coupangId = product.productId,
            company = company,
            name = product.name,
            category = MacbookCategory.MACBOOK_AIR,
            chip = chip,
            cpu = cpu,
            gpu = gpu,
            storage = storage,
            memory = memory,
            language = language,
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
