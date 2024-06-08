package backend.itracker.crawl.service.mapper.macbook

import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.macbook.domain.MacbookCategory
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_BOOK_AIR_M3_13 = "MacBook Air M3 13 기본형"
private const val MAC_BOOK_AIR_M3_13_CTO = "MacBook Air 13 M3 CTO"
private const val MAC_BOOK_AIR_M3_15 = "MacBook Air M3 15 기본형"
private const val MAC_BOOK_AIR_M3_15_CTO = "MacBook Air 15 M3 CTO"

@Component
class MacbookAirM3Mapper : MacbookMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return subCategory == MAC_BOOK_AIR_M3_13 ||
                subCategory == MAC_BOOK_AIR_M3_13_CTO ||
                subCategory == MAC_BOOK_AIR_M3_15 ||
                subCategory == MAC_BOOK_AIR_M3_15_CTO
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
        val gpu = names[3].split(" ").first()
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
