package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val ULTRA_2_ALPINE = "Ultra2 알파인 루프"
private const val ULTRA_2_TRAIL = "Ultra2 트레일루프"
private val supportsList = listOf(
    ULTRA_2_ALPINE,
    ULTRA_2_TRAIL,
)

@Component
class AppleWatchUltra2Mapper : AppleWatchMappingComponent {

    override fun supports(subCategory: String): Boolean {
        return supportsList.contains(subCategory)
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

        val bandSize = names[2]

        val size = names[3].replace("mm", "")

        val isCellular = names[4].contains("Cellular")

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
