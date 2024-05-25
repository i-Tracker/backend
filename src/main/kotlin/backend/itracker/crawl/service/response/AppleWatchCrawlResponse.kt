package backend.itracker.crawl.service.response

import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import backend.itracker.crawl.watch.domain.AppleWatchPrice
import java.math.BigDecimal

data class AppleWatchCrawlResponse(
    val coupangId: Long,
    val releaseYear: Int,
    val company: String,
    val name: String,
    val category: AppleWatchCategory,
    val size: String,
    val caseType: String,
    val color: String,
    val band: String,
    val bandSize: String,
    val isCellular: Boolean,
    val productLink: String,
    val thumbnail: String,
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean
) {

    fun toDomain(): AppleWatch {
        val appleWatch = AppleWatch(
            coupangId = coupangId,
            releaseYear = releaseYear,
            company = company,
            name = name,
            category = category,
            size = size.toInt(),
            caseType = caseType,
            color = color,
            band = band,
            bandSize = bandSize,
            isCellular = isCellular,
            productLink = productLink,
            thumbnail = thumbnail,
        )

        appleWatch.add(
            AppleWatchPrice(
                discountPercentage = discountPercentage,
                basePrice = basePrice,
                currentPrice = currentPrice,
                isOutOfStock = isOutOfStock
            )
        )
        return appleWatch
    }
}
