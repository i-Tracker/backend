package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val WATCH_SE_2_GPS_40 = "Watch SE 2 GPS 40mm"
private const val WATCH_SE_2_GPS_44 = "Watch SE 2 GPS 44mm"
private const val WATCH_SE_2_CELLULAR_40 = "Watch SE 2 Cellular 40mm"
private const val WATCH_SE_2_CELLULAR_44 = "Watch SE 2 Cellular 44mm"

private val supportsList = listOf(
    WATCH_SE_2_GPS_40,
    WATCH_SE_2_GPS_44,
    WATCH_SE_2_CELLULAR_40,
    WATCH_SE_2_CELLULAR_44
)

@Component
class AppleWatchSE2Mapper : AppleWatchMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return supportsList.contains(subCategory)
    }

    override fun toDomain(product: DefaultProduct): AppleWatch {
        val names = product.name.split(",")
            .map { it.trim() }
            .toList()

        // todo: refactor this
        val title = names[0].split(" ")
        val company = title[0]
        val releaseYear = title[1]
        val category = AppleWatchCategory.APPLE_WATCH_SE_2
        val caseType = title[5]

        val size = names[1].replace("mm", "")

        val isCellular = names[2].contains("Cellular")

        val caseColorAndBand = names[3].split("/")
            .map { it.trim() }
            .toList()
        val color = caseColorAndBand.first()
        val band = caseColorAndBand.last()

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
