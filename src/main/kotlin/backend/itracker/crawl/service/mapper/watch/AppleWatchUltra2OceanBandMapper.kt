package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val ULTRA_2_OCEAN = "Ultra2 오션밴드"

@Component
class AppleWatchUltra2OceanBandMapper : AppleWatchMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return ULTRA_2_OCEAN == subCategory
    }

    override fun toDomain(product: DefaultProduct): AppleWatch {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = "2023"
        val category = AppleWatchCategory.APPLE_WATCH_ULTRA_2
        val caseType = title[4]
        val color = title[4]

        val band = "${title[5]} ${title[6]} ${names[1]}"


        val size = names[2].replace("mm", "")

        val isCellular = names[3].contains("Cellular")

        val bandSize = names[4]

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
