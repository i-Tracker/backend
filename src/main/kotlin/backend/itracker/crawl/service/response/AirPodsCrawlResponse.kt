package backend.itracker.crawl.service.response

import backend.itracker.crawl.airpods.domain.AirPods
import backend.itracker.crawl.airpods.domain.AirPodsCategory
import backend.itracker.crawl.airpods.domain.AirPodsPrice
import java.math.BigDecimal

data class AirPodsCrawlResponse(
    val coupangId: Long,
    val name: String,
    val company: String,
    val releaseYear: Int,
    val category: AirPodsCategory,
    val color : String,
    val generation: Int,
    val canWirelessCharging: Boolean,
    val chargingType: String,
    val productLink: String,
    val thumbnail: String,
    val basePrice: BigDecimal,
    val discountPercentage: Int,
    val currentPrice: BigDecimal,
    val isOutOfStock: Boolean
) {
    constructor(
        coupangId: Long,
        name: String,
        company: String,
        releaseYear: String,
        category: AirPodsCategory,
        color: String = "",
        generation: String,
        canWirelessCharging: Boolean,
        chargingType: String,
        productLink: String,
        thumbnail: String,
        basePrice: BigDecimal,
        discountPercentage: Int,
        currentPrice: BigDecimal,
        isOutOfStock: Boolean
    ) : this(
        coupangId = coupangId,
        name = name,
        color = color,
        company = company,
        releaseYear = releaseYear.toInt(),
        category = category,
        generation = generation.toInt(),
        canWirelessCharging = canWirelessCharging,
        chargingType = chargingType,
        productLink = productLink,
        thumbnail = thumbnail,
        discountPercentage = discountPercentage,
        basePrice = basePrice,
        currentPrice = currentPrice,
        isOutOfStock = isOutOfStock
    )

    fun toDomain(): AirPods {
        return AirPods(
            coupangId = coupangId,
            name = name,
            company = company,
            color = color,
            releaseYear = releaseYear,
            category = category,
            generation = generation,
            canWirelessCharging = canWirelessCharging,
            chargingType = chargingType,
            productLink = productLink,
            thumbnail = thumbnail
        ).apply {
            addPrice(
                AirPodsPrice(
                    discountPercentage = discountPercentage,
                    basePrice = basePrice,
                    currentPrice = currentPrice,
                    isOutOfStock = isOutOfStock
                )
            )
        }
    }

}
