package backend.itracker.crawl.service.mapper.watch

import backend.itracker.crawl.service.response.AppleWatchCrawlResponse
import backend.itracker.crawl.service.vo.DefaultProduct
import backend.itracker.crawl.watch.domain.AppleWatch
import backend.itracker.crawl.watch.domain.AppleWatchCategory
import org.springframework.stereotype.Component

private const val ALUMINUM_SPORTS_BAND_9_CELLULAR_41 = "알루미늄 스포츠밴드 9 Cellular 41mm"
private const val ALUMINUM_SPORTS_BAND_9_CELLULAR_45 = "알루미늄 스포츠밴드 9 Cellular 45mm"
private const val ALUMINUM_SPORTS_LOOP_9_CELLULAR_41 = "알루미늄 스포츠루프 9 Cellular 41mm"
private const val ALUMINUM_SPORTS_LOOP_9_CELLULAR_45 = "알루미늄 스포츠루프 9 Cellular 45mm"
private const val STAINLESS_SPORTS_BAND_9_CELLULAR_41 = "스테인리스 스포츠밴드 9 Cellular 41mm"
private const val STAINLESS_SPORTS_BAND_9_CELLULAR_45 = "스테인리스 스포츠밴드 9 Cellular 45mm"
private const val STAINLESS_MILANESE_BAND_9_CELLULAR_41 = "스테인리스 밀레니즈 루프 9 41mm"
private const val STAINLESS_MILANESE_BAND_9_CELLULAR_45 = "스테인리스 밀레니즈 루프 9 45mm"

private val supportsList = listOf(
    ALUMINUM_SPORTS_BAND_9_CELLULAR_41,
    ALUMINUM_SPORTS_BAND_9_CELLULAR_45,
    ALUMINUM_SPORTS_LOOP_9_CELLULAR_41,
    ALUMINUM_SPORTS_LOOP_9_CELLULAR_45,
    STAINLESS_SPORTS_BAND_9_CELLULAR_41,
    STAINLESS_SPORTS_BAND_9_CELLULAR_45,
    STAINLESS_MILANESE_BAND_9_CELLULAR_41,
    STAINLESS_MILANESE_BAND_9_CELLULAR_45
)

@Component
class AppleWatchSeries9CellularMapper : AppleWatchMappingComponent {

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
        val category = AppleWatchCategory.APPLE_WATCH_SERIES_9
        val isCellular = title[3].contains("Cellular")

        val size = names[1].replace("mm", "")

        val caseType = names[2]

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
