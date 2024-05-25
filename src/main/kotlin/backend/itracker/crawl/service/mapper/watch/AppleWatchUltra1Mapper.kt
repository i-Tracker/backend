package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val ULTRA_1 = "Watch Ultra 1"

@Component
class AppleWatchUltra1Mapper : AppleWatchMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return ULTRA_1 == subCategory
    }

    override fun toDomain(product: DefaultProduct): AppleWatch {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = "2022"
        val category = AppleWatchCategory.APPLE_WATCH_ULTRA_1
        val size = title[5].replace("mm", "")
        val isCellular = title[6].contains("Cellular")
        val color = title[7]
        val caseType = title[7]

        val bandColorAndBandSize = names[1].split(" ")
        val band = "${title[3]} ${title[4]} ${bandColorAndBandSize.first()}"
        val bandSize = bandColorAndBandSize.last()

        return AppleWatchCrawlResponse(
            coupangId = product.productId,
            releaseYear = releaseYear.toInt(),
            company = company,
            name = product.name,
            category = category,
            size = size,
            caseType = caseType,
            color = color,
            band = band,
            bandSize = bandSize,
            isCellular = isCellular,
            productLink = product.productLink,
            thumbnail = product.thumbnailLink,
            discountPercentage = product.price.discountPercentage,
            basePrice = product.price.basePrice,
            currentPrice = product.price.discountPrice,
            isOutOfStock = product.price.isOutOfStock
        ).toDomain()
    }
}
