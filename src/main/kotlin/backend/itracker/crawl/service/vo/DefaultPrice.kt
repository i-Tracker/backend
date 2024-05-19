package backend.itracker.crawl.service.vo

import java.math.BigDecimal

data class DefaultPrice(
    val discountPercentage: Int,
    val basePrice: BigDecimal,
    val discountPrice: BigDecimal,
)

