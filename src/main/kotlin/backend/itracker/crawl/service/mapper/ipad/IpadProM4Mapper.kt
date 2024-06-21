package backend.itracker.crawl.service.mapper.ipad

import backend.itracker.crawl.ipad.domain.Ipad
import backend.itracker.crawl.ipad.domain.IpadCategory
import backend.itracker.crawl.service.response.IpadCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import org.springframework.stereotype.Component

@Component
class IpadProM4Mapper : IpadMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return subCategory.contains("iPad Pro M4")
    }

    override fun toDomain(product: DefaultProduct): Ipad {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[2]
        val category = IpadCategory.I_PAD_PRO
        val chip = "M4"
        val size = title[5]
        val generation = "5"
        val glass = title[7]
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
            glass = glass,
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
