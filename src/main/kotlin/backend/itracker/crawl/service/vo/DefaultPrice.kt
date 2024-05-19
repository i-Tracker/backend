package backend.itracker.crawl.service.vo

import java.math.BigDecimal

data class DefaultPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
    val isOutOfStock: String,
) {
    constructor(priceInformation: List<String>) : this(
        discountPercentage = priceInformation[0].replace("%", "").toInt(),
        basePrice = priceInformation[1]
            .replace(",", "")
            .removeSuffix("원").toBigDecimal(),
        discountPrice = 1L.toBigDecimal(),
        isOutOfStock = ""
    )
}
