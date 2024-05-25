package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val WATCH_SERIES_8_GPS_CELLULAR = "Watch Series 8 GPS+Cellular"

@Component
class AppleWatchSeries8Mapper : AppleWatchMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return WATCH_SERIES_8_GPS_CELLULAR == subCategory
    }

    override fun toDomain(product: DefaultProduct): AppleWatch {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = "2022"
        val category = AppleWatchCategory.APPLE_WATCH_SERIES_8
        val isCellular = title[3].contains("Cellular")

        val size = names[1].replace("mm", "")

        val caseType = names[2]

        val caseColorAndBand = names[3].split("/")
            .map { it.trim() }
            .toList()
        val color = caseColorAndBand.first()
        val band = caseColorAndBand.last()

        val bandSize = ""

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
