package backend.itracker.crawl.service.mapper.macbook

import backend.itracker.crawl.common.ProductCategory
import backend.itracker.crawl.macbook.domain.Macbook
import backend.itracker.crawl.service.response.MacbookCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val MAC_BOOK_PRO_M3_16_CTO = "MacBook Pro 16 M3 CTO"

@Component
class MacbookProM316CTOMapper : MacbookMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return subCategory == MAC_BOOK_PRO_M3_16_CTO
    }

    override fun toDomain(product: DefaultProduct): Macbook {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1].toInt()
        val size = title[4].toInt()
        val chip = "${title[5]} ${title[6]}"
        val cpu = title[7]
        val gpu = title[9]
        val color = names[1]
        val storage = names[2]
        val memory = names[3]
        val language = names[4]

        return MacbookCrawlResponse(
            coupangId = product.productId,
            company = company,
            name = product.name,
            category = ProductCategory.MACBOOK_PRO,
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
