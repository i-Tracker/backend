package backend.itracker.crawl.service.mapper.ipad

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.ipad.domain.IpadCategory
import backend.itracker.crawl.service.response.IpadCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

private const val IPAD_PRO_6_GEN = "iPad Pro 12.9 6세대"
private const val IPAD_PRO_4_GEN = "iPad Pro 11 4세대"

@Component
class IpadProMapper : IpadMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return IPAD_PRO_6_GEN == subCategory ||
                IPAD_PRO_4_GEN == subCategory
    }

    override fun toDomain(product: DefaultProduct): Ipad {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[2]
        val category = IpadCategory.I_PAD_PRO
        val size = title[5]
        val generation = title[6].replace("세대", "")
        val chip = title[7].replace("칩", "")
        val color = names[1]
        val storage = names[2]
        val isCellular = names[3].contains("Cellular")

        return IpadCrawlResponse(
            coupangId = product.productId,
            company = company,
            name = product.name,
            category = category,
            chip = chip,
            storage = storage,
            color = color,
            size = size,
            releaseYear = releaseYear.toInt(),
            isCellular = isCellular,
            generation = generation,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
                    basePrice = product.price.basePrice,
            discountPercentage = product.price.discountPercentage,
            currentPrice = product.price.discountPrice,
            isOutOfStock = product.price.isOutOfStock
        ).toDomain()
    }
}
